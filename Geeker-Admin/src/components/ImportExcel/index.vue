<template>
  <el-dialog v-model="dialogVisible" :title="`${parameter.title}`" :destroy-on-close="true" width="580px" draggable>
    <el-form class="drawer-multiColumn-form" label-width="100px">
      <el-form-item label="模板下载 :">
        <el-button type="primary" :icon="Download" @click="downloadTemp">点击下载</el-button>
      </el-form-item>
      <el-form-item label="文件上传 :">
        <el-upload
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
          ref="upload"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <template #tip>
            <div class="el-upload__tip">请上传 .xls , .xlsx 标准格式文件</div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="导入方式 :">
        <el-radio-group v-model="importType">
          <el-tooltip class="box-item" effect="dark" content="自动判断数据量过多时采用过滤失败数据的方案" placement="top">
            <el-radio label="auto">自动</el-radio>
          </el-tooltip>
          <el-tooltip class="box-item" effect="dark" content="所有一起成功或失败" placement="top">
            <el-radio label="all">全部导入</el-radio>
          </el-tooltip>
          <el-tooltip
            class="box-item"
            effect="dark"
            content="允许校验通过的数据导入成功,校验不通过的数据导入失败"
            placement="top"
          >
            <el-radio label="filter_fail">过滤失败数据</el-radio>
          </el-tooltip>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="数据处理 :">
        <el-radio-group v-model="dataHandleType">
          <el-tooltip class="box-item" effect="dark" content="所有数据导入都是一条新的数据" placement="top">
            <el-radio label="insert">新增</el-radio>
          </el-tooltip>
          <el-tooltip class="box-item" effect="dark" content="重复数据直接覆盖" placement="top">
            <el-radio label="cover">导入覆盖</el-radio>
          </el-tooltip>
          <el-tooltip class="box-item" effect="dark" content="重复数据更新信息" placement="top">
            <el-radio label="update">导入更新</el-radio>
          </el-tooltip>
        </el-radio-group>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="impResultVisible" @close="handleResultClose" title="导入结果" width="30%">
    <span>导入成功数据</span>
    <el-tag type="success">{{ impResult.successCnt }}</el-tag>
    <span>条,</span>
    <span>失败数据</span>
    <el-tag type="danger">{{ impResult.failCnt }}</el-tag>
    <span>条</span>
    <el-button type="danger" link v-show="impResult.failCnt > 0" @click="downloadErrorReport(impResult.errorId)"
      >,点击查看错误报告</el-button
    >
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="impResultVisible = false"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts" name="ImportExcel">
import { ref } from "vue";
import { useDownload } from "@/hooks/useDownload";
import { Download } from "@element-plus/icons-vue";
import { ElNotification } from "element-plus";
import { errorReport } from "@/api/modules/common";

export interface ExcelParameterProps {
  title: string; // 标题
  tempApi: (params: any) => Promise<any>; // 下载模板的Api
  importApi: (params: any) => Promise<any>; // 批量导入的Api
  getTableList?: () => Promise<any>; // 获取表格数据的Api
  template: string;
}

// 是否覆盖数据
const importType = ref("auto");
const dataHandleType = ref("insert");
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
  useDownload(parameter.value.tempApi, `${parameter.value.title}模板`, { name: parameter.value.template }, false);
};
const downloadErrorReport = (id: string) => {
  useDownload(errorReport, `${parameter.value.title}错误报告`, { id: id }, false);
};

const impResultVisible = ref(false);
const impResult = ref({ importType: "", successCnt: 0, failCnt: 0, errorId: "" });
// 文件上传
const uploadExcel = async (param: any) => {
  let excelFormData = new FormData();
  excelFormData.append("file", param.file);
  excelFormData.append("importType", importType.value as unknown as Blob);
  excelFormData.append("dataHandleType", dataHandleType.value as unknown as Blob);
  const res = await parameter.value.importApi!(excelFormData);
  impResult.value = res.data;
  impResultVisible.value = true;
};
const handleResultClose = () => {
  if (impResult.value.failCnt <= 0) {
    dialogVisible.value = false;
    parameter.value.getTableList && parameter.value.getTableList();
  } else {
    upload.value.clearFiles();
  }
};

/**
 * @description 文件上传之前判断
 * @param file 上传的文件
 * */
const beforeExcelUpload = (file: any) => {
  const isExcel =
    file.type === "application/vnd.ms-excel" || file.type === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
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
@import "./index.scss";
</style>
