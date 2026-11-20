<template>
  <el-drawer v-model="drawerProps.visible" :destroy-on-close="true" size="50%" :title="drawerProps.title">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="row.name" maxlength="50" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input-number v-model="row.age" :min="1" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="分数" prop="score">
        <el-input-number v-model="row.score" :min="1" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="row.gender" placeholder="请选择" clearable>
          <el-option v-for="item in GenderEnum" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="启用状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio value="Enable">是</el-radio>
          <el-radio value="Disable">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="兴趣" prop="interest">
        <!-- <DictSelect v-model="row.interest" dict-key="sys_interest" /> -->
        <DictCheckbox v-model="row.interest" dict-key="sys_interest" />
      </el-form-item>
      <el-form-item label="生日" prop="birth">
        <el-date-picker
          v-model="row.birth"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          :disabled-date="disabledDateFun"
          placeholder="请选择日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="注册时间" prop="registTime">
        <el-date-picker
          type="datetime"
          v-model="row.registTime"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          :disabled-date="disabledDateTimeFun"
          placeholder="请选择日期时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:image-url="row.avatar"></UploadImg>
      </el-form-item>
      <el-form-item label="视频" prop="video">
        <UploadVideo v-model:video-url="row.video" :disabled="drawerProps.isView"></UploadVideo>
      </el-form-item>
      <el-form-item label="照片" prop="photos">
        <UploadImgs v-model:file-list="row.photos"></UploadImgs>
      </el-form-item>
      <el-form-item label="附件" prop="attachment">
        <UploadFiles
          v-model:file-list="row.attachment"
          :disabled="drawerProps.isView"
          :limit="5"
          :file-size="10"
          :file-type="['.txt', '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.zip', '.rar']"
        ></UploadFiles>
      </el-form-item>
      <el-form-item label="单文件" prop="fileInput">
        <UploadFile
          v-model:file-info="row.fileInput"
          :disabled="drawerProps.isView"
          :file-size="10"
          :file-type="['.txt']"
        ></UploadFile>
      </el-form-item>
      <el-form-item label="简介" prop="introduction">
        <el-input
          type="textarea"
          :rows="3"
          v-model="row.introduction"
          maxlength="255"
          show-word-limit
          placeholder="请填写"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="详情" prop="detail">
        <WangEditor :disabled="drawerProps.isView" height="400px" v-model:value="row.detail" />
      </el-form-item>
      <el-form-item label="手机" prop="phone">
        <el-input v-model="row.phone" maxlength="11" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="js" name="SysDemoDrawer">
import { ref, reactive } from "vue";
import { ElMessage } from "element-plus";

import WangEditor from "@/components/WangEditor/index.vue";
import UploadImg from "@/components/Upload/Img.vue";
import UploadVideo from "@/components/Upload/Video.vue";
import UploadImgs from "@/components/Upload/Imgs.vue";
import UploadFiles from "@/components/Upload/Files.vue";
import UploadFile from "@/components/Upload/File.vue";
import DictSelect from "@/components/dict/DictSelect.vue";
import DictCheckbox from "@/components/dict/DictCheckbox.vue";
import { GenderEnum } from "@/enums/commonEnum";
import { add, edit } from "./api";

const rules = reactive({
  name: [{ required: true, message: "请填写名称", trigger: "change" }],
  registTime: [{ required: true, message: "请填写注册时间", trigger: "change" }],
  avatar: [{ required: true, message: "请上传头像", trigger: "change" }],
  introduction: [{ required: true, message: "请填写简介", trigger: "change" }],
  phone: [{ required: true, pattern: /^\d{11}$/, message: "手机格式错误", trigger: "change" }],
  state: [{ required: true, message: "状态必填", trigger: "change" }]
});

// drawer框状态
const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
});
const row = ref({});

// 接收父组件传过来的参数
const acceptParams = (rowData, refreshTable, isView) => {
  row.value = rowData;
  drawerProps.value.refreshTable = refreshTable;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看系统示列";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统示列" : "新增系统示列";
  }
  drawerProps.value.visible = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    const api = row.value && row.value.id ? edit : add;
    try {
      const { message } = await api(row.value);
      ElMessage.success({ message: message });
      drawerProps.value.refreshTable();
      drawerProps.value.visible = false;
    } catch (error) {
      console.log(error);
    }
  });
};

const disabledDateFun = time => {
  return time.getTime() < Date.now();
};
const disabledDateTimeFun = time => {
  return time.getTime() < Date.now() - 3600 * 1000 * 24;
};

defineExpose({
  acceptParams
});
</script>
