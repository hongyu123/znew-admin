<template>
  <div class="upload-box">
    <div class="file-item">
      <el-upload
        action="#"
        :class="['upload', self_disabled ? 'disabled' : '']"
        :multiple="false"
        :disabled="self_disabled"
        :show-file-list="false"
        :http-request="handleHttpUpload"
        :before-upload="beforeUpload"
        :on-success="uploadSuccess"
        :on-error="uploadError"
        :accept="acceptStr"
      >
        <el-button type="primary" :icon="Upload" :disabled="self_disabled">选择文件</el-button>
      </el-upload>

      <span v-if="fileInfo?.url" class="file-operation">
        <el-icon class="file-icon"><Document /></el-icon>
        <span class="file-name" :title="fileInfo.name">{{ fileInfo.name }}</span>
        <el-button type="primary" link :icon="Download" @click="handleDownload">下载</el-button>
        <el-button v-if="!self_disabled" type="danger" link :icon="Delete" @click="deleteFile">删除</el-button>
      </span>
    </div>
    <div class="el-upload__tip">
      <slot name="tip">{{ tip }}</slot>
    </div>
  </div>
</template>

<script setup lang="ts" name="UploadFile">
import type { UploadProps, UploadRequestOptions, UploadUserFile } from "element-plus";
import { ElNotification, formContextKey, formItemContextKey } from "element-plus";
import { computed, inject, ref, watch } from "vue";
import { Delete, Download, Upload } from "@element-plus/icons-vue";

import { upload } from "@/api/modules/common";
import { downloadFile, isAcceptFile, isWithinSize } from "./helper";

interface UploadFileProps {
  fileInfo; // 文件地址 ==> 非必传
  api?: (params: any) => Promise<any>; // 上传文件的 api 方法 ==> 非必传（默认使用通用上传接口）
  disabled?: boolean; // 是否禁用上传组件 ==> 非必传（默认为 false）
  fileSize?: number; // 文件大小限制（M） ==> 非必传（默认为 10M）
  fileType?: string[]; // 文件类型限制，支持扩展名与 MIME 类型 ==> 非必传（默认为常见文档类型）
}

const props = withDefaults(defineProps<UploadFileProps>(), {
  disabled: false,
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
const tip = computed(() => `支持 ${props.fileType.join(" / ")} 格式，大小不超过 ${props.fileSize}M`);

const emit = defineEmits<{
  "update:fileInfo";
}>();

/**
 * @description 文件上传
 * @param options upload 所有配置项
 * */
const handleHttpUpload = async (options: UploadRequestOptions) => {
  const formData = new FormData();
  formData.append("file", options.file);
  const api = props.api ?? upload;
  const { data } = await api(formData);
  emit("update:fileInfo", data);
  // 调用 el-form 内部的校验方法（可自动校验）
  if (formItemContext?.prop) {
    formContext?.validateField([formItemContext.prop as string]);
  }
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
 * */
const uploadSuccess = () => {
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
 * @description 删除文件
 * */
const deleteFile = () => {
  emit("update:fileInfo", {});
};

/**
 * @description 下载文件
 * */
const handleDownload = () => {
  downloadFile(props.fileInfo.url, props.fileInfo.name);
};
</script>

<style scoped lang="scss">
.upload-box {
  .disabled {
    :deep(.el-upload) {
      cursor: not-allowed;
    }
  }
  .file-item {
    display: flex;
    align-items: center;
    gap: 8px;
    width: 100%;
    padding: 4px 8px;
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
    .file-operation {
      background-color: var(--el-fill-color-lighter);
    }
  }
  .el-upload__tip {
    line-height: 18px;
  }
}
</style>
