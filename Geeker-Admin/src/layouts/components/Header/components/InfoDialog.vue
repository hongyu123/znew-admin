<template>
  <el-dialog v-model="dialogVisible" title="个人信息" width="500px" draggable>
    <el-form ref="ruleFormRef" label-width="100px" label-suffix=" :" :rules="rules" :model="rowData">
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="rowData!.phone" maxlength="11" show-word-limit placeholder="请填写手机号码" clearable></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="rowData!.nickname" maxlength="50" show-word-limit placeholder="请填写昵称" clearable></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:imageUrl="rowData!.avatar" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="rowData!.email" maxlength="50" show-word-limit placeholder="请填写邮箱" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="rowData!.gender">
          <el-radio value="Male">男</el-radio>
          <el-radio value="Female">女</el-radio>
        </el-radio-group>
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
import UploadImg from "@/components/Upload/Img.vue";
import { userInfo, editInfo } from "@/views/sys/sysUser/api.js";
import { useUserStore } from "@/stores/modules/user";

const rowData = ref<any>({});
const userStore = useUserStore();

const dialogVisible = ref(false);
const openDialog = () => {
  dialogVisible.value = true;
  userInfo().then(res => {
    rowData.value = res.data;
  });
};

const rules = reactive({
  phone: [{ required: true, pattern: /^\d{11}$/, message: "手机号码格式错误", trigger: "change" }],
  nickname: [{ required: true, message: "请填写昵称", trigger: "change" }],
  avatar: [{ required: true, message: "请上传头像", trigger: "change" }],
  email: [{ pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: "邮箱格式错误", trigger: "change" }]
});

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await editInfo(rowData.value);
      // 修改成功后立即同步当前登录用户的头像
      userStore.setUserInfo({ nickname: rowData.value.nickname || "", avatar: rowData.value.avatar || "" });
      ElMessage.success({ message: message });
      dialogVisible.value = false;
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({ openDialog });
</script>
