<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}admin日志`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="drawerProps.rowData!.title"
          maxlength="50"
          show-word-limit
          placeholder="请填写标题"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="方法名" prop="method">
        <el-input
          v-model="drawerProps.rowData!.method"
          maxlength="255"
          show-word-limit
          placeholder="请填写方法名"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="管理员账号" prop="adminAccount">
        <el-input
          v-model="drawerProps.rowData!.adminAccount"
          maxlength="50"
          show-word-limit
          placeholder="请填写管理员账号"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="请求url" prop="requestUrl">
        <el-input
          v-model="drawerProps.rowData!.requestUrl"
          maxlength="255"
          show-word-limit
          placeholder="请填写请求url"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="request_ip" prop="requestIp">
        <el-input
          v-model="drawerProps.rowData!.requestIp"
          maxlength="128"
          show-word-limit
          placeholder="请填写request_ip"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="请求体" prop="requestBody">
        <el-input
          v-model="drawerProps.rowData!.requestBody"
          maxlength="2,000"
          show-word-limit
          placeholder="请填写请求体"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="请求头" prop="requestHeader">
        <el-input
          v-model="drawerProps.rowData!.requestHeader"
          maxlength="255"
          show-word-limit
          placeholder="请填写请求头"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="响应" prop="response">
        <el-input
          v-model="drawerProps.rowData!.response"
          maxlength="2,000"
          show-word-limit
          placeholder="请填写响应"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="异常状态" prop="state">
        <el-input-number v-model="drawerProps.rowData!.state" :min="1" placeholder="请填写异常状态" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="异常信息" prop="message">
        <el-input
          v-model="drawerProps.rowData!.message"
          maxlength="255"
          show-word-limit
          placeholder="请填写异常信息"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="耗时" prop="times"> </el-form-item>
      <el-form-item label="请求时间" prop="requestTime"> </el-form-item>
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
  title: [{ required: true, message: "请填写标题", trigger: "change" }],
  method: [{ required: true, message: "请填写方法名", trigger: "change" }],
  adminAccount: [{ required: true, message: "请填写管理员账号", trigger: "change" }],
  requestUrl: [{ required: true, message: "请填写请求url", trigger: "change" }],
  requestIp: [{ required: true, message: "请填写request_ip", trigger: "change" }],
  requestBody: [{ required: true, message: "请填写请求体", trigger: "change" }],
  response: [{ required: true, message: "请填写响应", trigger: "change" }],
  times: [{ required: true, message: "请填写耗时", trigger: "change" }]
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
