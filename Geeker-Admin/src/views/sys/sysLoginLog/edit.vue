<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}系统登录日志`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="用户账号" prop="account">
        <el-input
          v-model="drawerProps.rowData!.account"
          maxlength="50"
          show-word-limit
          placeholder="请填写用户账号"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="登录IP" prop="ip">
        <el-input
          v-model="drawerProps.rowData!.ip"
          maxlength="128"
          show-word-limit
          placeholder="请填写登录IP"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="登录地点" prop="location">
        <el-input
          v-model="drawerProps.rowData!.location"
          maxlength="255"
          show-word-limit
          placeholder="请填写登录地点"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="浏览器类型" prop="browser">
        <el-input
          v-model="drawerProps.rowData!.browser"
          maxlength="50"
          show-word-limit
          placeholder="请填写浏览器类型"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="操作系统" prop="os">
        <el-input
          v-model="drawerProps.rowData!.os"
          maxlength="50"
          show-word-limit
          placeholder="请填写操作系统"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="提示消息" prop="message">
        <el-input
          v-model="drawerProps.rowData!.message"
          maxlength="255"
          show-word-limit
          placeholder="请填写提示消息"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="登录时间" prop="loginTime"> </el-form-item>
      <el-form-item label="状态(1成功0失败)" prop="state">
        <el-input-number
          v-model="drawerProps.rowData!.state"
          :min="1"
          placeholder="请填写状态(1成功0失败)"
          clearable
        ></el-input-number>
      </el-form-item>
      <el-form-item label="在线状态" prop="onlineFlag">
        <el-input-number
          v-model="drawerProps.rowData!.onlineFlag"
          :min="1"
          placeholder="请填写在线状态"
          clearable
        ></el-input-number>
      </el-form-item>
      <el-form-item label="登出类型" prop="logoutType">
        <el-input-number
          v-model="drawerProps.rowData!.logoutType"
          :min="1"
          placeholder="请填写登出类型"
          clearable
        ></el-input-number>
      </el-form-item>
      <el-form-item label="登出时间" prop="logoutTime"> </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";

const rules = reactive({
  loginTime: [{ required: true, message: "请填写登录时间", trigger: "change" }]
});

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

// 接收父组件传过来的参数
const acceptParams = (params: DrawerProps): void => {
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
