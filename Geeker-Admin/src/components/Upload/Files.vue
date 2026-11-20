<template>
  <div class="upload-box">
    <el-upload
      v-model:file-list="_fileList"
      action="#"
      :class="['upload', self_disabled ? 'disabled' : '']"
      :multiple="true"
      :disabled="self_disabled"
      :show-file-list="false"
      :limit="limit"
      :http-request="handleHttpUpload"
      :before-upload="beforeUpload"
      :on-exceed="handleExceed"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :accept="acceptStr"
    >
      <el-button type="primary" :icon="Upload" :disabled="self_disabled">选择文件</el-button>
    </el-upload>
    <ul v-if="_fileList.length" class="file-list">
      <li v-for="file in _fileList" :key="file.uid ?? file.url" class="file-item">
        <el-icon class="file-icon"><Document /></el-icon>
        <span class="file-name" :title="file.name">{{ file.name }}</span>
        <div class="file-actions">
          <el-button type="primary" link :icon="Download" :disabled="!file.url" @click="handleDownload(file)">下载</el-button>
          <el-button v-if="!self_disabled" type="danger" link :icon="Delete" @click="removeFile(file)">删除</el-button>
        </div>
      </li>
    </ul>
    <div class="el-upload__tip">
      <slot name="tip">{{ tip }}</slot>
    </div>
  </div>
</template>

<script setup lang="ts" name="UploadFiles">
import type { UploadFile, UploadProps, UploadRequestOptions, UploadUserFile } from "element-plus";
import { ElNotification, formContextKey, formItemContextKey } from "element-plus";
import { computed, inject, ref, watch } from "vue";
import { Delete, Download, Upload } from "@element-plus/icons-vue";

import { upload } from "@/api/modules/common";
import { downloadFile, getFileNameFromUrl, isAcceptFile, isWithinSize } from "./helper";

interface UploadFilesProps {
  fileList?: UploadUserFile[]; // 文件列表 ==> 非必传
  api?: (params: any) => Promise<any>; // 上传文件的 api 方法 ==> 非必传（默认使用通用上传接口）
  disabled?: boolean; // 是否禁用上传组件 ==> 非必传（默认为 false）
  limit?: number; // 最大文件上传数 ==> 非必传（默认为 5 个）
  fileSize?: number; // 单个文件大小限制（M） ==> 非必传（默认为 10M）
  fileType?: string[]; // 文件类型限制，支持扩展名与 MIME 类型 ==> 非必传（默认为常见文档类型）
}

const props = withDefaults(defineProps<UploadFilesProps>(), {
  fileList: () => [],
  disabled: false,
  limit: 5,
  fileSize: 10,
  fileType: () => [".pdf", ".doc", ".docx", ".xls", ".xlsx", ".txt", ".zip", ".rar"]
});

// 获取 el-form 组件上下文
const formContext = inject(formContextKey, void 0);
// 获取 el-form-item 组件上下文
const formItemContext = inject(formItemContextKey, void 0);
// 判断是否禁用上传和删除
const self_disabled = computed(() => {
  return props.disabled || formContext?.disabled;
});
// 文件选择框的 accept 属性
const acceptStr = computed(() => props.fileType.join(","));
// 上传提示
const tip = computed(() => `最多上传 ${props.limit} 个文件，单个文件不超过 ${props.fileSize}M`);

// 文件列表（内部维护，与 el-upload 双向绑定）
const _fileList = ref<UploadUserFile[]>(props.fileList || []);

// 监听 props.fileList 列表默认值改变
watch(
  () => props.fileList,
  (n: UploadUserFile[]) => {
    _fileList.value = n || [];
  }
);

const emit = defineEmits<{
  "update:fileList": [value: UploadUserFile[]];
}>();

// 同步文件列表给父组件
const emitFileList = () => {
  emit("update:fileList", _fileList.value);
};

/**
 * @description 文件上传
 * @param options upload 所有配置项
 * */
const handleHttpUpload = async (options: UploadRequestOptions) => {
  const formData = new FormData();
  formData.append("file", options.file);
  const api = props.api ?? upload;
  const { data } = await api(formData);
  // 返回结果会作为 on-success 的 response 参数
  return data;
};

/**
 * @description 文件上传之前判断
 * @param rawFile 选择的文件
 * */
const beforeUpload: UploadProps["beforeUpload"] = rawFile => {
  const sizeOk = isWithinSize(rawFile.size, props.fileSize);
  const typeOk = isAcceptFile(rawFile, props.fileType);
  if (!typeOk)
    ElNotification({
      title: "温馨提示",
      message: "上传文件不符合所需的格式！",
      type: "warning"
    });
  if (!sizeOk)
    setTimeout(() => {
      ElNotification({
        title: "温馨提示",
        message: `上传文件大小不能超过 ${props.fileSize}M！`,
        type: "warning"
      });
    }, 0);
  return typeOk && sizeOk;
};

/**
 * @description 文件上传成功
 * @param response 上传响应结果
 * @param uploadFile 上传的文件
 * */
const uploadSuccess = (response: { id: number; name?: string; url?: string } | undefined, uploadFile: UploadFile) => {
  if (!response) return;
  uploadFile.uid = response.id;
  uploadFile.url = response.url;
  if (response.name) uploadFile.name = response.name;
  emitFileList();
  // 调用 el-form 内部的校验方法（可自动校验）
  if (formItemContext?.prop) {
    formContext?.validateField([formItemContext.prop as string]);
  }
  ElNotification({
    title: "温馨提示",
    message: "文件上传成功！",
    type: "success"
  });
};

/**
 * @description 文件上传错误
 * */
const uploadError = () => {
  ElNotification({
    title: "温馨提示",
    message: "文件上传失败，请您重新上传！",
    type: "error"
  });
};

/**
 * @description 文件数超出
 * */
const handleExceed = () => {
  ElNotification({
    title: "温馨提示",
    message: `当前最多只能上传 ${props.limit} 个文件，请移除后上传！`,
    type: "warning"
  });
};

/**
 * @description 删除文件
 * @param file 删除的文件
 * */
const removeFile = (file: UploadUserFile) => {
  _fileList.value = _fileList.value.filter(item => item.uid !== file.uid);
  emitFileList();
};

/**
 * @description 下载文件
 * @param file 下载的文件
 * */
const handleDownload = (file: UploadUserFile) => {
  downloadFile(file.url ?? "", file.name || getFileNameFromUrl(file.url ?? ""));
};
</script>

<style scoped lang="scss">
.upload-box {
  .disabled {
    :deep(.el-upload) {
      cursor: not-allowed;
    }
  }
  .file-list {
    width: 100%;
    margin: 8px 0 0;
    padding: 0;
    list-style: none;
  }
  .file-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 4px 8px;
    margin-bottom: 4px;
    background-color: var(--el-fill-color-lighter);
    border-radius: 4px;
    .file-icon {
      flex-shrink: 0;
      font-size: 16px;
      color: var(--el-color-primary);
    }
    .file-name {
      flex: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .file-actions {
      display: flex;
      flex-shrink: 0;
      align-items: center;
    }
  }
  .el-upload__tip {
    line-height: 18px;
  }
}
</style>
