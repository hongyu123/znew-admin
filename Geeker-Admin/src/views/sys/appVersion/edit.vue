<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}app版本管理`">
    <el-form
      ref="ruleFormRef"
      label-width="150px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="设备" prop="device">
        <el-radio-group v-model="drawerProps.rowData!.device">
          <el-radio label="android">android</el-radio>
          <el-radio label="ios">ios</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="版本" prop="version">
        <el-input
          v-model="drawerProps.rowData!.version"
          maxlength="20"
          show-word-limit
          placeholder="请填写版本"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input
          type="textarea"
          :rows="3"
          v-model="drawerProps.rowData!.description"
          maxlength="255"
          show-word-limit
          placeholder="请填写描述"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="是否强制更新" prop="forceUpdate">
        <el-radio-group v-model="drawerProps.rowData!.forceUpdate">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="apk文件" prop="downloadUrl" v-show="drawerProps.rowData!.device=='android'">
        <UploadFile v-model:fileUrl="drawerProps.rowData!.downloadUrl" :api="upload" accept=".apk"></UploadFile>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive, computed } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { upload } from "@/api/modules/common";
import UploadFile from "@/components/Upload/File.vue";

interface DrawerProps {
  title: string;
  isView: boolean;
  rowData?: any;
  api?: (params: any) => Promise<any>;
  getTableList?: () => Promise<any>;
}

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  isView: false,
  title: ""
});

const downloadUrlRequired = computed(() => {
  if (drawerProps.value.rowData && drawerProps.value.rowData.device == "android") {
    return true;
  }
  return false;
});
const rules = reactive({
  device: [{ required: true, message: "请填写设备(android,ios)", trigger: "change" }],
  version: [{ required: true, message: "请填写版本", trigger: "change" }],
  forceUpdate: [{ required: true, message: "请填写是否强制更新", trigger: "change" }],
  downloadUrl: [{ required: downloadUrlRequired, message: "请上传apl文件", trigger: "change" }]
});

// 接收父组件传过来的参数
const acceptParams = (params: DrawerProps): void => {
  if (!params.rowData.id) {
    params.rowData.device = "android";
    params.rowData.forceUpdate = 0;
  }
  drawerProps.value = params;
  drawerVisible.value = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await drawerProps.value.api!(drawerProps.value.rowData);
      ElMessage.success({ message: message });
      drawerProps.value.getTableList!();
      drawerVisible.value = false;
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({
  acceptParams
});
</script>
