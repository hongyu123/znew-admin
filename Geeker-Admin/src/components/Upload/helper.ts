/**
 * @description 判断文件类型是否符合限制（支持扩展名与 MIME 类型）
 * @param file 文件
 * @param fileType 允许的扩展名或 MIME 类型，如 [".txt", "image/png", "video/*"]
 */
export const isAcceptFile = (file: File, fileType: string[] = []): boolean => {
  if (!fileType.length) return true;
  const fileName = (file.name || "").toLowerCase();
  const fileMime = (file.type || "").toLowerCase();
  return fileType.some(type => {
    const item = type.trim().toLowerCase();
    if (!item) return false;
    // 扩展名匹配
    if (item.startsWith(".")) return fileName.endsWith(item);
    // 通配 MIME 类型匹配，如 video/*
    if (item.endsWith("/*")) return fileMime.startsWith(item.slice(0, -1));
    // 完整 MIME 类型匹配
    return fileMime === item;
  });
};

/**
 * @description 判断文件大小是否在限制范围内
 * @param size 文件大小（字节）
 * @param fileSize 大小限制（M）
 */
export const isWithinSize = (size: number, fileSize: number): boolean => {
  return size / 1024 / 1024 <= fileSize;
};

/**
 * @description 从文件地址中获取文件名
 * @param url 文件地址
 */
export const getFileNameFromUrl = (url: string): string => {
  if (!url) return "";
  const name = url.split("?")[0].split("/").pop() || "";
  try {
    return decodeURIComponent(name);
  } catch {
    return name;
  }
};

/**
 * @description 下载文件（优先以原文件名下载，获取 blob 失败时直接打开文件地址）
 * @param url 文件地址
 * @param name 文件名
 */
export const downloadFile = async (url: string, name?: string): Promise<void> => {
  if (!url) return;
  // 优先使用传入的文件名，其次从地址中解析，最后兜底一个默认名称
  const fileName = name || getFileNameFromUrl(url) || "download";

  try {
    // 通过 blob 下载，避免浏览器直接打开文件（如图片、PDF）
    const response = await fetch(url);
    if (!response.ok) throw new Error(`下载失败，状态码：${response.status}`);
    const blob = await response.blob();
    const navigatorAny = window.navigator as Navigator & {
      msSaveOrOpenBlob?: (blob: Blob, fileName: string) => void;
    };

    // 兼容 edge 不支持 createObjectURL 方法
    if (navigatorAny.msSaveOrOpenBlob) {
      navigatorAny.msSaveOrOpenBlob(blob, fileName);
      return;
    }

    const blobUrl = window.URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.style.display = "none";
    link.download = fileName;
    link.href = blobUrl;
    document.body.appendChild(link);
    link.click();

    // 去除下载对 url 的影响，延迟释放避免部分浏览器下载中断
    document.body.removeChild(link);
    window.URL.revokeObjectURL(blobUrl);
  } catch (error) {
    // 获取 blob 失败（如跨域限制）时直接打开文件地址
    console.warn("下载文件失败，改为直接打开文件地址：", error);
    window.open(url, "_blank");
  }
};
