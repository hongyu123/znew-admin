<script setup>
import { reactive, ref, onMounted } from "vue";
import { message } from "@/utils/message";
import ReCropperPreview from "@/components/ReCropperPreview";
import { createFormData, deviceDetection } from "@pureadmin/utils";
import uploadLine from "@iconify-icons/ri/upload-line";
import { useUserStoreHook } from "@/store/modules/user";
import { userKey } from "@/utils/auth";
import { storageLocal } from "@pureadmin/utils";
import { enums, upload } from "@/api/sys/common";
import { userInfo, editInfo } from "@/views/sys/sysUser/sysUser";

defineOptions({
  name: "Profile"
});

const imgSrc = ref("");
const cropperBlob = ref();
const cropRef = ref();
const uploadRef = ref();
const isShow = ref(false);
const userInfoFormRef = ref();

const userInfos = ref({
  avatar: "",
  nickname: "",
  email: "",
  phone: "",
  description: ""
});
const emit = defineEmits(["refresh"]);

const rules = reactive({
  phone: [
    { required: false, pattern: /^\d{11}$/, message: "手机号码格式错误" }
  ],
  email: [
    {
      pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
      message: "邮箱格式错误"
    }
  ]
});

const genderEnums = ref([]);
onMounted(() => {
  enums("Gender").then(res => {
    genderEnums.value = res.data;
  });
  userInfo().then(res => {
    userInfos.value = res.data;
  });
});

const onChange = uploadFile => {
  const reader = new FileReader();
  reader.onload = e => {
    imgSrc.value = e.target.result;
    isShow.value = true;
  };
  reader.readAsDataURL(uploadFile.raw);
};

const handleClose = () => {
  cropRef.value.hidePopover();
  uploadRef.value.clearFiles();
  isShow.value = false;
};

const onCropper = ({ blob }) => (cropperBlob.value = blob);

const handleSubmitImage = () => {
  const formData = createFormData({
    files: new File([cropperBlob.value], "avatar")
  });
  upload(formData)
    .then(({ code, data }) => {
      if (code) {
        userInfos.value.avatar = data.url;
        message("更新头像成功", { type: "success" });
        handleClose();
      } else {
        message("更新头像失败");
      }
    })
    .catch(error => {
      message(`提交异常 ${error}`, { type: "error" });
    });
};

// 更新信息
const onSubmit = async formEl => {
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      await editInfo(userInfos.value);
      const storageUserInfo = storageLocal().getItem(userKey);
      useUserStoreHook().SET_AVATAR(userInfos.value.avatar);
      useUserStoreHook().SET_NICKNAME(userInfos.value.nickname);
      storageUserInfo.avatar = userInfos.value.avatar;
      storageUserInfo.nickname = userInfos.value.nickname;
      storageLocal().setItem(userKey, storageUserInfo);
      emit("refresh");
      message("更新信息成功", { type: "success" });
    }
  });
};
</script>

<template>
  <div
    :class="[
      'min-w-[180px]',
      deviceDetection() ? 'max-w-[100%]' : 'max-w-[70%]'
    ]"
  >
    <h3 class="my-8">个人信息</h3>
    <el-form
      ref="userInfoFormRef"
      label-position="top"
      :rules="rules"
      :model="userInfos"
    >
      <el-form-item label="头像">
        <el-avatar :size="80" :src="userInfos.avatar" />
        <el-upload
          ref="uploadRef"
          accept="image/*"
          action="#"
          :limit="1"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="onChange"
        >
          <el-button plain class="ml-4">
            <IconifyIconOffline :icon="uploadLine" />
            <span class="ml-2">更新头像</span>
          </el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model="userInfos.nickname"
          maxlength="50"
          placeholder="请填写昵称"
          clearable
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="userInfos.phone"
          maxlength="11"
          placeholder="请填写手机号码"
          clearable
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="userInfos.email"
          show-word-limit
          placeholder="请填写邮箱"
          clearable
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="userInfos.gender">
          <el-radio
            v-for="item in genderEnums"
            :key="item.value"
            :value="item.value"
          >
            {{ item.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="简介" prop="remark">
        <el-input
          v-model="userInfos.remark"
          maxlength="255"
          show-word-limit
          placeholder="请输入简介"
          clearable
          type="textarea"
          :autosize="{ minRows: 6, maxRows: 8 }"
        />
      </el-form-item>
      <el-button type="primary" @click="onSubmit(userInfoFormRef)">
        更新信息
      </el-button>
    </el-form>
    <el-dialog
      v-model="isShow"
      width="40%"
      title="编辑头像"
      destroy-on-close
      :closeOnClickModal="false"
      :before-close="handleClose"
      :fullscreen="deviceDetection()"
    >
      <ReCropperPreview ref="cropRef" :imgSrc="imgSrc" @cropper="onCropper" />
      <template #footer>
        <div class="dialog-footer">
          <el-button bg text @click="handleClose">取消</el-button>
          <el-button bg text type="primary" @click="handleSubmitImage">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
