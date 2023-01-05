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
        <UploadImg v-model:imageUrl="rowData!.avatar" :api="upload" width="250px">
          <template #empty>
            <el-icon><Picture /></el-icon>
            <span>请上传图片</span>
          </template>
        </UploadImg>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="rowData!.email" maxlength="50" show-word-limit placeholder="请填写邮箱" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="rowData!.gender">
          <el-radio label="Male">男</el-radio>
          <el-radio label="Female">女</el-radio>
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
import { upload } from "@/api/modules/common";
import UploadImg from "@/components/Upload/Img.vue";
import { current, edit } from "@/api/sys/sysUser";
import { GlobalStore } from "@/stores";

const dialogVisible = ref(false);

const rowData = ref<any>({});
// openDialog
const openDialog = () => {
  dialogVisible.value = true;
  current().then(res => {
    rowData.value = res.data;
  });
};

const rules = reactive({
  phone: [{ required: false, pattern: /^\d{11}$/, message: "手机号码格式错误", trigger: "change" }],
  email: [{ pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: "邮箱格式错误", trigger: "change" }]
});

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await edit!(rowData.value);
      ElMessage.success({ message: message });
      dialogVisible.value = false;
      const globalStore = GlobalStore();
      globalStore.userInfo.avatar = rowData.value.avatar;
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({ openDialog });
</script>
