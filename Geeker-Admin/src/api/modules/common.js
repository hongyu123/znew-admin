import http from "@/api";

export const enums = (code, filter, type) => {
  return http.get("/common/enums", { code, filter, type });
};

export const upload = data => {
  return http.post("/common/upload", data, {
    // 多个文件可同时上传，关闭重复请求取消
    cancel: false,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};
