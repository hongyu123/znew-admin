<template>
  <el-upload
    ref="uploadRef"
    v-model:file-list="_fileList"
    action="#"
    :drag="drag"
    :multiple="limit > 1"
    class="!w-[200px]"
    :limit="limit"
    :accept="fileType"
    :http-request="handleHttpUpload"
    :before-upload="beforeUpload"
    :on-exceed="handleExceed"
    :on-success="uploadSuccess"
    :on-error="uploadError"
  >
    <div v-if="drag" class="el-upload__text">
      <IconifyIconOffline :icon="UploadIcon" width="26" class="m-auto mb-2" />
      可点击或拖拽上传
    </div>
    <el-button v-else type="primary">点击上传</el-button>
  </el-upload>
</template>
<script setup lang="ts" name="UploadFiles">
import { ref, computed, inject, watch } from "vue";
import type {
  UploadProps,
  UploadFile,
  UploadUserFile,
  UploadRequestOptions,
  UploadRawFile
} from "element-plus";
import {
  ElNotification,
  UploadInstance,
  formContextKey,
  formItemContextKey,
  genFileId
} from "element-plus";
import UploadIcon from "@iconify-icons/ri/upload-2-line";
import { upload } from "@/api/sys/common";

interface UploadFileProps {
  fileList: UploadUserFile[];
  api?: (params: any) => Promise<any>; // 上传图片的 api 方法，一般项目上传都是同一个 api 方法，在组件里直接引入即可 ==> 非必传
  drag?: boolean; // 是否支持拖拽上传 ==> 非必传（默认为 true）
  disabled?: boolean; // 是否禁用上传组件 ==> 非必传（默认为 false）
  limit?: number; // 最大图片上传数 ==> 非必传（默认为 5张）
  fileSize?: number; // 图片大小限制 ==> 非必传（默认为 5M）
  fileType?: string; // 图片类型限制 ==> 非必传（默认为 ["image/jpeg", "image/png", "image/gif"]）
}

const props = withDefaults(defineProps<UploadFileProps>(), {
  fileList: () => [],
  drag: true,
  disabled: false,
  limit: 5,
  fileSize: 10,
  fileType: "*/*"
});

// 获取 el-form 组件上下文
const formContext = inject(formContextKey, void 0);
// 获取 el-form-item 组件上下文
const formItemContext = inject(formItemContextKey, void 0);

const _fileList = ref<UploadUserFile[]>(props.fileList);

// 监听 props.fileList 列表默认值改变
watch(
  () => props.fileList,
  (n: UploadUserFile[]) => {
    _fileList.value = n;
  }
);

/**
 * @description 文件上传之前判断
 * @param rawFile 选择的文件
 * */
const beforeUpload: UploadProps["beforeUpload"] = rawFile => {
  const imgSize = rawFile.size / 1024 / 1024 < props.fileSize;
  const imgType = true;
  if (!imgType)
    ElNotification({
      title: "温馨提示",
      message: "上传图片不符合所需的格式！",
      type: "warning"
    });
  if (!imgSize)
    setTimeout(() => {
      ElNotification({
        title: "温馨提示",
        message: `上传图片大小不能超过 ${props.fileSize}M！`,
        type: "warning"
      });
    }, 0);
  return imgType && imgSize;
};

/**
 * @description 图片上传
 * @param options upload 所有配置项
 * */
const handleHttpUpload = async (options: UploadRequestOptions) => {
  let formData = new FormData();
  formData.append("file", options.file);
  try {
    const api = props.api ?? upload;
    const { data } = await api(formData);
    options.onSuccess(data);
  } catch (error) {
    options.onError(error as any);
  }
};

/**
 * @description 图片上传成功
 * @param response 上传响应结果
 * @param uploadFile 上传的文件
 * */
const emit = defineEmits<{
  "update:fileList": [value: UploadUserFile[]];
}>();
const uploadSuccess = (
  response: { url: string; id } | undefined,
  uploadFile
) => {
  if (!response) return;
  uploadFile.url = response.url;
  uploadFile.id = response.id;
  emit("update:fileList", _fileList.value);
  // 调用 el-form 内部的校验方法（可自动校验）
  formItemContext?.prop &&
    formContext?.validateField([formItemContext.prop as string]);
  ElNotification({
    title: "温馨提示",
    message: "图片上传成功！",
    type: "success"
  });
};

/**
 * @description 图片上传错误
 * */
const uploadError = () => {
  ElNotification({
    title: "温馨提示",
    message: "图片上传失败，请您重新上传！",
    type: "error"
  });
};

const uploadRef = ref<UploadInstance>();
/**
 * @description 文件数超出
 * */
const handleExceed: UploadProps["onExceed"] = files => {
  if (props.limit > 1) {
    ElNotification({
      title: "温馨提示",
      message: `当前最多只能上传 ${props.limit} 张图片，请移除后上传！`,
      type: "warning"
    });
    return true;
  }
  uploadRef.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  uploadRef.value!.handleStart(file);
  uploadRef.value!.submit();
};
</script>
