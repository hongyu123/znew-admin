<script setup>
import { ref } from "vue";
import router from "@/router";
import { message } from "@/utils/message";
import { deviceDetection } from "@pureadmin/utils";
import { changePassword } from "@/views/sys/sysUser/sysUser";

defineOptions({
  name: "ChangePassword"
});

const userInfos = ref({});
const userInfoFormRef = ref();

const onSubmit = async () => {
  await userInfoFormRef.value.validate(async (valid, fields) => {
    if (valid) {
      await changePassword(userInfos.value);
      message("修改成功", { type: "success" });
      router.replace("/login");
    }
  });
};
const comfirmPassword = [
  {
    validator: (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入确认密码"));
      } else if (userInfos.value.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    },
    required: true,
    trigger: "blur"
  }
];
</script>

<template>
  <div
    :class="[
      'min-w-[180px]',
      deviceDetection() ? 'max-w-[100%]' : 'max-w-[70%]'
    ]"
  >
    <el-form ref="userInfoFormRef" label-position="top" :model="userInfos">
      <el-form-item
        label="原密码"
        prop="oldPassword"
        :rules="{
          required: true,
          pattern: /^\w{6,16}$/,
          message: '密码格式错误'
        }"
      >
        <el-input
          v-model="userInfos.oldPassword"
          type="password"
          show-password
          placeholder="请输入原密码"
          clearable
        />
      </el-form-item>
      <el-form-item
        label="新密码"
        prop="password"
        :rules="{
          required: true,
          pattern: /^\w{6,16}$/,
          message: '密码格式错误'
        }"
      >
        <el-input
          v-model="userInfos.password"
          type="password"
          show-password
          placeholder="请输入新密码"
          clearable
        />
      </el-form-item>
      <el-form-item label="确认密码" prop="password2" :rules="comfirmPassword">
        <el-input
          v-model="userInfos.password2"
          type="password"
          show-password
          placeholder="请输入确认密码"
          clearable
        />
      </el-form-item>
      <el-button type="primary" @click="onSubmit"> 确认修改 </el-button>
    </el-form>
  </div>
</template>
