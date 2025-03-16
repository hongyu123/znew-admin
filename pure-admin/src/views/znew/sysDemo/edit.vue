<template>
  <el-drawer
    v-model="drawerProps.visible"
    class="plus-drawer-form"
    :destroy-on-close="true"
    size="50%"
    :title="drawerProps.title"
  >
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="名称" prop="name" class="plus-form-item">
        <el-input
          v-model="row.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="年龄" prop="age" class="plus-form-item">
        <el-input-number
          v-model="row.age"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="分数" prop="score" class="plus-form-item">
        <el-input-number
          v-model="row.score"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender" class="plus-form-item">
        <el-select
          v-model="row.gender"
          clearable
          placeholder="请选择Gender"
          class="plus-form-item-field"
        >
          <el-option
            v-for="item in genderEnums"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="启用状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="兴趣" prop="interestList">
        <el-checkbox-group v-model="row.interestList">
          <el-checkbox value="1" label="篮球" />
          <el-checkbox value="2" label="足球" />
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="生日" prop="birth" class="plus-form-item">
        <el-date-picker
          v-model="row.birth"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          placeholder="请选择"
          class="plus-form-item-field"
          :disabledDate="disabledDateFun"
        />
      </el-form-item>
      <el-form-item label="注册时间" prop="registTime" class="plus-form-item">
        <el-date-picker
          v-model="row.registTime"
          type="datetime"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择"
          class="plus-form-item-field"
          :disabledDate="disabledDateTimeFun"
        />
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:imageUrl="row.avatar" />
      </el-form-item>
      <el-form-item label="视频" prop="video" class="plus-form-item">
        <el-input
          v-model="row.video"
          maxlength="200"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="照片" prop="photosList">
        <UploadImgMul v-model:fileList="row.photosList" />
      </el-form-item>
      <el-form-item label="附件" prop="attachmentList">
        <UploadFiles v-model:fileList="row.attachmentList" />
      </el-form-item>
      <el-form-item label="简介" prop="introduction" class="plus-form-item">
        <el-input
          v-model="row.introduction"
          type="textarea"
          :rows="3"
          maxlength="255"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="详情" prop="detail">
        <WangEditor
          v-model:value="row.detail"
          :disabled="drawerProps.isView"
          height="400px"
        />
      </el-form-item>
      <el-form-item label="手机" prop="phone" class="plus-form-item">
        <el-input
          v-model="row.phone"
          maxlength="11"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="地址" prop="location" class="plus-form-item">
        <el-input
          v-model="row.location"
          maxlength="200"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="经度" prop="lng" class="plus-form-item">
        <el-input-number
          v-model="row.lng"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="纬度" prop="lat" class="plus-form-item">
        <el-input-number
          v-model="row.lat"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="文件输入" prop="fileInput" class="plus-form-item">
        <el-input
          v-model="row.fileInput"
          maxlength="200"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button
        v-show="!drawerProps.isView"
        type="primary"
        @click="handleSubmit"
        >确定</el-button
      >
    </template>
  </el-drawer>
</template>

<script setup name="EditDrawer">
import "plus-pro-components/es/components/drawer-form/style/css";
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";

import UploadImg from "@/components/Upload/Img.vue";
import UploadImgMul from "@/components/Upload/Imgs.vue";
import UploadFiles from "@/components/Upload/Files.vue";
import WangEditor from "@/components/Editor/WangEditor.vue";

import { enums } from "@/api/sys/common";
import { add, edit } from "./sysDemo";
const genderEnums = ref([]);
onMounted(() => {
  enums("Gender").then(res => {
    genderEnums.value = res.data;
  });
});

const rules = reactive({
  name: [{ required: true, message: "请填写名称" }],
  registTime: [{ required: true, message: "请填写注册时间" }],
  avatar: [{ required: true, message: "请上传头像" }],
  introduction: [{ required: true, message: "请填写简介" }],
  phone: [{ required: true, pattern: /^\d{11}$/, message: "手机格式错误" }]
});

const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
  //getTableList
});
const row = ref({});

// 接收父组件传过来的参数
const acceptParams = (rowData, getTableList, isView) => {
  row.value = rowData;
  drawerProps.value.getTableList = getTableList;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看系统示例表";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统示例表" : "新增系统示例表";
  }
  drawerProps.value.visible = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
      const api = row.value && row.value.id ? edit : add;
      await api(row.value);
      ElMessage.success({ message: `${drawerProps.value.title}成功！` });
      drawerProps.value.getTableList();
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
