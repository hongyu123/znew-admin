<template>
  <div class="card content-box">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="客服电话" prop="phone">
        <el-input
          v-model="drawerProps.rowData!.phone"
          maxlength="255"
          show-word-limit
          placeholder="请填写客服电话"
          clearable
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="resetForm"> 取消 </el-button>
        <el-button type="primary" @click="handleSubmit"> 确定 </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";

import { gets, sets } from "@/api/sys/sysConfig";

const rules = reactive({
  phone: [{ required: true, message: "请填写客服电话", trigger: "change" }]
});

interface DrawerProps {
  title: string;
  isView: boolean;
  rowData?: any;
  api?: (params: any) => Promise<any>;
  getTableList?: () => Promise<any>;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  title: "",
  api: sets,
  rowData: {}
});

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await drawerProps.value.api!(drawerProps.value.rowData);
      ElMessage.success({ message: message });
    } catch (error) {
      console.log(error);
    }
  });
};

onMounted(() => {
  loadData();
});
const loadData = () => {
  gets().then(res => {
    drawerProps.value.rowData = res.data;
  });
};

//重置表单
const resetForm = () => {
  //ruleFormRef.value?.resetFields();
  loadData();
};
</script>
<style scoped lang="scss">
@import "@/views/form/basicForm/index.scss";
</style>
