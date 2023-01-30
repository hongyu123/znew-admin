<template>
  <div>
    <video style="max-height: 200px" :src="imageUrl" controls="true">您的浏览器不支持 video 标签。</video>
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
      :accept="fileType"
    >
      <el-button type="primary">
        上传<el-icon class="el-icon--right"><Upload /></el-icon>
      </el-button>
    </el-upload>
  </div>
</template>

<script setup lang="ts" name="UploadImg">
import { ref, computed, inject } from "vue";
import { upload } from "@/api/modules/common";
import { generateUUID } from "@/utils/util";
import { ElNotification, formContextKey, formItemContextKey } from "element-plus";
import type { UploadProps, UploadRequestOptions } from "element-plus";

type FileTypes = "video/*";

interface UploadFileProps {
  imageUrl: string; // 图片地址 ==> 必传
  api?: (params: any) => Promise<any>; // 上传图片的 api 方法，一般项目上传都是同一个 api 方法，在组件里直接引入即可 ==> 非必传
  drag?: boolean; // 是否支持拖拽上传 ==> 非必传（默认为 true）
  disabled?: boolean; // 是否禁用上传组件 ==> 非必传（默认为 false）
  fileSize?: number; // 图片大小限制 ==> 非必传（默认为 5M）
  fileType?: FileTypes; // 图片类型限制 ==> 非必传（默认为 ["image/jpeg", "image/png", "image/gif"]）
  height?: string; // 组件高度 ==> 非必传（默认为 150px）
  width?: string; // 组件宽度 ==> 非必传（默认为 150px）
  borderRadius?: string; // 组件边框圆角 ==> 非必传（默认为 8px）
}

// 接受父组件参数
const props = withDefaults(defineProps<UploadFileProps>(), {
  imageUrl: "",
  drag: true,
  disabled: false,
  fileSize: 10,
  fileType: "video/*",
  height: "150px",
  width: "200px",
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

/**
 * @description 图片上传
 * @param options 上传的文件
 * */
interface UploadEmits {
  (e: "update:imageUrl", value: string): void;
  (e: "check-validate"): void;
}
const emit = defineEmits<UploadEmits>();
const handleHttpUpload = async (options: UploadRequestOptions) => {
  let formData = new FormData();
  formData.append("file", options.file);
  try {
    const api = props.api ?? upload;
    const { data } = await api(formData);
    emit("update:imageUrl", data.url);
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
  //const imgType = props.fileType;
  if (!rawFile.type.includes("video"))
    ElNotification({
      title: "温馨提示",
      message: "上传视频不符合所需的格式！",
      type: "warning"
    });
  if (!imgSize)
    ElNotification({
      title: "温馨提示",
      message: `上传视频大小不能超过 ${props.fileSize}M！`,
      type: "warning"
    });
  return rawFile.type.includes("video") && imgSize;
};

// 图片上传成功提示
const uploadSuccess = () => {
  ElNotification({
    title: "温馨提示",
    message: "视频上传成功！",
    type: "success"
  });
};

// 图片上传错误提示
const uploadError = () => {
  ElNotification({
    title: "温馨提示",
    message: "视频上传失败，请您重新上传！",
    type: "error"
  });
};
</script>
