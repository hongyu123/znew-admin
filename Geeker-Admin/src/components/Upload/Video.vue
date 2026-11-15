<template>
  <div class="upload-box">
    <div v-if="videoUrl" class="video-preview">
      <video :src="videoUrl" class="video-player" controls preload="metadata"></video>
      <div v-if="!self_disabled" class="video-actions">
        <el-upload
          action="#"
          :multiple="false"
          :disabled="self_disabled"
          :show-file-list="false"
          :http-request="handleHttpUpload"
          :before-upload="beforeUpload"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :accept="acceptStr"
        >
          <el-button type="primary" link :icon="RefreshRight">更换视频</el-button>
        </el-upload>
        <el-button type="danger" link :icon="Delete" @click="deleteVideo">删除</el-button>
      </div>
    </div>
    <el-upload
      v-else
      action="#"
      :class="['upload', self_disabled ? 'disabled' : '']"
      :multiple="false"
      :disabled="self_disabled"
      :show-file-list="false"
      :http-request="handleHttpUpload"
      :before-upload="beforeUpload"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :drag="drag"
      :accept="acceptStr"
    >
      <div class="upload-empty">
        <slot name="empty">
          <el-icon><VideoCamera /></el-icon>
          <span>请上传视频</span>
        </slot>
      </div>
    </el-upload>
    <div class="el-upload__tip">
      <slot name="tip">{{ tip }}</slot>
    </div>
  </div>
</template>

<script setup lang="ts" name="UploadVideo">
import type { UploadProps, UploadRequestOptions } from "element-plus";
import { ElNotification, formContextKey, formItemContextKey } from "element-plus";
import { computed, inject } from "vue";
import { Delete, RefreshRight } from "@element-plus/icons-vue";

import { upload } from "@/api/modules/common";
import { isAcceptFile, isWithinSize } from "./helper";

interface UploadVideoProps {
  videoUrl?: string; // 视频地址 ==> 非必传
  api?: (params: any) => Promise<any>; // 上传视频的 api 方法 ==> 非必传（默认使用通用上传接口）
  drag?: boolean; // 是否支持拖拽上传 ==> 非必传（默认为 true）
  disabled?: boolean; // 是否禁用上传组件 ==> 非必传（默认为 false）
  fileSize?: number; // 视频大小限制（M） ==> 非必传（默认为 100M）
  fileType?: string[]; // 视频类型限制 ==> 非必传（默认为常见视频格式）
  width?: string; // 视频宽度 ==> 非必传（默认为 360px）
  height?: string; // 视频高度 ==> 非必传（默认为 200px）
  borderRadius?: string; // 视频边框圆角 ==> 非必传（默认为 8px）
}

const props = withDefaults(defineProps<UploadVideoProps>(), {
  videoUrl: "",
  drag: true,
  disabled: false,
  fileSize: 100,
  fileType: () => [
    "video/mp4",
    "video/webm",
    "video/ogg",
    "video/quicktime",
    ".mp4",
    ".webm",
    ".ogg",
    ".mov",
    ".avi",
    ".mkv",
    ".flv"
  ],
  width: "360px",
  height: "200px",
  borderRadius: "8px"
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
  "update:videoUrl": [value: string];
}>();

/**
 * @description 视频上传
 * @param options upload 所有配置项
 * */
const handleHttpUpload = async (options: UploadRequestOptions) => {
  const formData = new FormData();
  formData.append("file", options.file);
  const api = props.api ?? upload;
  const { data } = await api(formData);
  emit("update:videoUrl", data.url ?? data.fileUrl);
  // 调用 el-form 内部的校验方法（可自动校验）
  if (formItemContext?.prop) {
    formContext?.validateField([formItemContext.prop as string]);
  }
};

/**
 * @description 视频上传之前判断
 * @param rawFile 选择的文件
 * */
const beforeUpload: UploadProps["beforeUpload"] = rawFile => {
  const sizeOk = isWithinSize(rawFile.size, props.fileSize);
  const typeOk = isAcceptFile(rawFile, props.fileType);
  if (!typeOk)
    ElNotification({
      title: "温馨提示",
      message: "上传视频不符合所需的格式！",
      type: "warning"
    });
  if (!sizeOk)
    setTimeout(() => {
      ElNotification({
        title: "温馨提示",
        message: `上传视频大小不能超过 ${props.fileSize}M！`,
        type: "warning"
      });
    }, 0);
  return typeOk && sizeOk;
};

/**
 * @description 视频上传成功
 * */
const uploadSuccess = () => {
  ElNotification({
    title: "温馨提示",
    message: "视频上传成功！",
    type: "success"
  });
};

/**
 * @description 视频上传错误
 * */
const uploadError = () => {
  ElNotification({
    title: "温馨提示",
    message: "视频上传失败，请您重新上传！",
    type: "error"
  });
};

/**
 * @description 删除视频
 * */
const deleteVideo = () => {
  emit("update:videoUrl", "");
};
</script>

<style scoped lang="scss">
.is-error {
  .upload {
    :deep(.el-upload),
    :deep(.el-upload-dragger) {
      border: 1px dashed var(--el-color-danger) !important;
      &:hover {
        border-color: var(--el-color-primary) !important;
      }
    }
  }
}
.upload-box {
  .disabled {
    :deep(.el-upload) {
      cursor: not-allowed;
    }
  }
  :deep(.upload) {
    .el-upload {
      position: relative;
      display: flex;
      align-items: center;
      justify-content: center;
      width: v-bind(width);
      height: v-bind(height);
      overflow: hidden;
      border: 1px dashed var(--el-border-color-darker);
      border-radius: v-bind(borderRadius);
      transition: var(--el-transition-duration-fast);
      &:hover {
        border-color: var(--el-color-primary);
      }
      .el-upload-dragger {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 100%;
        padding: 0;
        overflow: hidden;
        background-color: transparent;
        border: 1px dashed var(--el-border-color-darker);
        border-radius: v-bind(borderRadius);
        &:hover {
          border: 1px dashed var(--el-color-primary);
        }
      }
      .el-upload-dragger.is-dragover {
        background-color: var(--el-color-primary-light-9);
        border: 2px dashed var(--el-color-primary) !important;
      }
      .upload-empty {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        font-size: 12px;
        line-height: 30px;
        color: var(--el-color-info);
        .el-icon {
          font-size: 28px;
          color: var(--el-text-color-secondary);
        }
      }
    }
  }
  .video-preview {
    display: flex;
    flex-direction: column;
    gap: 6px;
  }
  .video-player {
    display: block;
    width: v-bind(width);
    height: v-bind(height);
    object-fit: contain;
    background-color: #000;
    border-radius: v-bind(borderRadius);
  }
  .video-actions {
    display: flex;
    align-items: center;
    gap: 4px;
  }
  .el-upload__tip {
    line-height: 18px;
  }
}
</style>
