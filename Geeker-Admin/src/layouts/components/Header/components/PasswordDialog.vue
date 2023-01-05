<template>
  <el-dialog v-model="dialogVisible" title="修改密码" width="500px" draggable>
    <el-form ref="ruleFormRef" label-width="100px" label-suffix=" :" :rules="rules" :model="rowData">
      <el-form-item label="原密码" prop="old_password">
        <el-input v-model="rowData!.old_password" type="password" placeholder="请填写原密码" clearable></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="rowData!.password" type="password" placeholder="请填写密码" clearable></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirm_password">
        <el-input v-model="rowData!.confirm_password" type="password" placeholder="请填写确认密码" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { changePassword } from "@/api/sys/sysUser";
import { LOGIN_URL } from "@/config/config";
import router from "@/routers";
import { GlobalStore } from "@/stores";
const globalStore = GlobalStore();

const dialogVisible = ref(false);

const rowData = ref<any>({});
const password = (rule: any, value: any, callback: any) => {
  if (!/^\w{6,16}$/.test(value)) {
    callback(new Error("密码格式错误"));
  }
  if (rowData.value.old_password == value) {
    callback(new Error("新密码不能和原密码一致"));
  } else {
    callback();
  }
};
const confirmPassword = (rule: any, value: any, callback: any) => {
  if (rowData.value.password !== value) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};
const rules = reactive({
  old_password: [{ required: true, pattern: /^\w{6,16}$/, message: "原密码格式错误", trigger: "change" }],
  password: [{ required: true, validator: password, trigger: "change" }],
  confirm_password: [{ required: true, validator: confirmPassword, trigger: "change" }]
});

// openDialog
const openDialog = () => {
  dialogVisible.value = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await changePassword(rowData.value);
      ElMessage.success({ message: message });
      dialogVisible.value = false;
      globalStore.setToken("");
      router.replace(LOGIN_URL);
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({ openDialog });
</script>
