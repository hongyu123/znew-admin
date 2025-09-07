<template>
  <el-dialog
    v-model="dialogVisible"
    :title="`${parameter.title}`"
    :destroy-on-close="true"
    width="580px"
    draggable
  >
    <el-form class="drawer-multiColumn-form" label-width="100px">
      <el-form-item label="模板下载 :">
        <el-button type="primary" :icon="Download" @click="downloadTemp"
          >点击下载</el-button
        >
      </el-form-item>
      <el-form-item label="文件上传 :">
        <el-upload
          ref="upload"
          action="string"
          class="upload"
          :drag="true"
          :limit="excelLimit"
          :multiple="true"
          :show-file-list="true"
          :http-request="uploadExcel"
          :before-upload="beforeExcelUpload"
          :on-exceed="handleExceed"
          accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <template #tip>
            <div class="el-upload__tip">请上传 .xls , .xlsx 标准格式文件</div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup lang="ts" name="ImportExcel">
import { ref } from "vue";
import { Download } from "@element-plus/icons-vue";
import { ElNotification, ElMessage } from "element-plus";
import { downloadByData } from "@pureadmin/utils";

export interface ExcelParameterProps {
  title: string; // 标题
  tempApi: (params: any) => Promise<any>; // 下载模板的Api
  importApi: (params: any) => Promise<any>; // 批量导入的Api
  getTableList?: () => Promise<any>; // 获取表格数据的Api
  template: string;
}

// 最大文件上传数
const excelLimit = ref(1);
// dialog状态
const dialogVisible = ref(false);
// 父组件传过来的参数
const parameter = ref<Partial<ExcelParameterProps>>({});

// 接收父组件参数
const acceptParams = (params?: any): void => {
  parameter.value = params;
  dialogVisible.value = true;
};

// Excel 导入模板下载
const downloadTemp = () => {
  if (!parameter.value.tempApi) return;
  parameter.value.tempApi({ id: 0 }).then(response => {
    if (response.headers && response.headers["content-disposition"]) {
      const fileName = decodeURI(
        response.headers["content-disposition"].replace(
          "attachment;filename=",
          ""
        )
      );
      downloadByData(response.data, `模板_${fileName}`);
    } else {
      downloadByData(response, `模板_${parameter.value.title}.xlsx`);
    }
  });
};

// 文件上传
const uploadExcel = async (param: any) => {
  let excelFormData = new FormData();
  excelFormData.append("file", param.file);
  const res = await parameter.value.importApi!(excelFormData);
  upload.value.clearFiles();
  dialogVisible.value = false;
  ElMessage({
    type: "success",
    message: res.message
  });
  parameter.value.getTableList && parameter.value.getTableList();
};

/**
 * @description 文件上传之前判断
 * @param file 上传的文件
 * */
const beforeExcelUpload = (file: any) => {
  const isExcel =
    file.type === "application/vnd.ms-excel" ||
    file.type ===
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  const fileSize = file.size / 1024 / 1024 < 10;
  if (!isExcel)
    ElNotification({
      title: "温馨提示",
      message: "上传文件只能是 xls / xlsx 格式！",
      type: "warning"
    });
  if (!fileSize)
    ElNotification({
      title: "温馨提示",
      message: "上传文件大小不能超过 10MB！",
      type: "warning"
    });
  return isExcel && fileSize;
};

// 文件数超出提示
const handleExceed = (): void => {
  ElNotification({
    title: "温馨提示",
    message: "最多只能上传一个文件！",
    type: "warning"
  });
};
const upload = ref();

/*
:on-success="excelUploadSuccess"
          :on-error="excelUploadError"
// 上传错误提示
const excelUploadError = (): void => {
  ElNotification({
    title: "温馨提示",
    message: `批量添加${parameter.value.title}失败，请您重新上传！`,
    type: "error"
  });
};

// 上传成功提示
const excelUploadSuccess = (): void => {
  ElNotification({
    title: "温馨提示",
    message: `批量添加${parameter.value.title}成功！`,
    type: "success"
  });
};*/

defineExpose({
  acceptParams
});
</script>
<style lang="scss" scoped>
.upload {
  width: 80%;
}
</style>
