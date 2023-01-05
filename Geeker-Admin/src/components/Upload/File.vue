<template>
  <span>
    <el-link type="success" :href="fileUrl">{{ fileName }}</el-link>
    <el-upload
      action="#"
      :id="uuid"
      :multiple="false"
      :disabled="self_disabled"
      :show-file-list="false"
      :http-request="handleHttpUpload"
      :before-upload="beforeUpload"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :accept="accept"
    >
      <el-button type="primary">
        Upload<el-icon class="el-icon--right"><Upload /></el-icon>
      </el-button>
    </el-upload>
  </span>
</template>

<script setup lang="ts" name="UploadImg">
import { ref, computed, inject } from "vue";
import { upload } from "@/api/modules/common";
import { generateUUID } from "@/utils/util";
import { ElNotification, formContextKey, formItemContextKey } from "element-plus";
import type { UploadProps, UploadRequestOptions } from "element-plus";

interface UploadFileProps {
  fileUrl: string; // 图片地址 ==> 必传
  api?: (params: any) => Promise<any>; // 上传图片的 api 方法，一般项目上传都是同一个 api 方法，在组件里直接引入即可 ==> 非必传
  drag?: boolean; // 是否支持拖拽上传 ==> 非必传（默认为 true）
  disabled?: boolean; // 是否禁用上传组件 ==> 非必传（默认为 false）
  inputDisabled?: boolean;
  fileSize?: number; // 图片大小限制 ==> 非必传（默认为 5M）
  accept?: string; // 图片类型限制 ==> 非必传（默认为 ["image/jpeg", "image/png", "image/gif"]）
  height?: string; // 组件高度 ==> 非必传（默认为 150px）
  width?: string; // 组件宽度 ==> 非必传（默认为 150px）
  borderRadius?: string; // 组件边框圆角 ==> 非必传（默认为 8px）
}

// 接受父组件参数
const props = withDefaults(defineProps<UploadFileProps>(), {
  fileUrl: "",
  drag: true,
  disabled: false,
  inputDisabled: false,
  fileSize: 10,
  accept: "*/*",
  height: "150px",
  width: "150px",
  borderRadius: "8px"
});

// 生成组件唯一id
const uuid = ref("id-" + generateUUID());

// 获取 el-form 组件上下文
const formContext = inject(formContextKey, void 0);
// 获取 el-form-item 组件上下文
const formItemContext = inject(formItemContextKey, void 0);
// 判断是否禁用上传和删除
const self_disabled = computed(() => {
  return props.disabled || formContext?.disabled;
});
//文件名
const fileName = computed(() => {
  if (!props.fileUrl) {
    return "";
  }
  return props.fileUrl.substring(props.fileUrl.lastIndexOf("/") + 1);
});

/**
 * @description 文件上传
 * @param options 上传的文件
 * */
interface UploadEmits {
  (e: "update:fileUrl", value: string): void;
  (e: "check-validate"): void;
}
const emit = defineEmits<UploadEmits>();
const handleHttpUpload = async (options: UploadRequestOptions) => {
  let formData = new FormData();
  formData.append("file", options.file);
  try {
    const api = props.api ?? upload;
    const { data } = await api(formData);
    emit("update:fileUrl", data.url);
    // 调用 el-form 内部的校验方法（可自动校验）
    formItemContext?.prop && formContext?.validateField([formItemContext.prop as string]);
    emit("check-validate");
  } catch (error) {
    options.onError(error as any);
  }
};

/**
 * @description 文件上传之前判断
 * @param rawFile 上传的文件
 * */
const beforeUpload: UploadProps["beforeUpload"] = rawFile => {
  const imgSize = rawFile.size / 1024 / 1024 < props.fileSize;
  if (!imgSize)
    ElNotification({
      title: "温馨提示",
      message: `上传图片大小不能超过 ${props.fileSize}M！`,
      type: "warning"
    });
  return imgSize;
};

// 文件上传成功提示
const uploadSuccess = () => {
  ElNotification({
    title: "温馨提示",
    message: "文件上传成功！",
    type: "success"
  });
};

// 文件上传错误提示
const uploadError = () => {
  ElNotification({
    title: "温馨提示",
    message: "文件上传失败，请您重新上传！",
    type: "error"
  });
};
</script>
