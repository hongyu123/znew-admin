<template>
  <el-drawer
    v-model="drawerProps.visible"
    class="plus-drawer-form"
    :destroy-on-close="true"
    size="30%"
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
      <el-form-item label="账号" prop="account" class="plus-form-item">
        <el-input
          v-model="row.account"
          maxlength="50"
          show-word-limit
          placeholder="请填写账号"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        v-show="!row.id"
        label="密码"
        prop="password"
        class="plus-form-item"
        :rules="{
          required: !row.id,
          pattern: /^\w{6,16}$/,
          message: '密码格式错误'
        }"
      >
        <el-input
          v-model="row.password"
          type="password"
          placeholder="请填写密码"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone" class="plus-form-item">
        <el-input
          v-model="row.phone"
          maxlength="11"
          show-word-limit
          placeholder="请填写手机号码"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname" class="plus-form-item">
        <el-input
          v-model="row.nickname"
          maxlength="50"
          show-word-limit
          placeholder="请填写昵称"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="头像" prop="avatar" class="plus-form-item">
        <UploadImg v-model:imageUrl="row.avatar" />
      </el-form-item>
      <el-form-item label="状态" prop="state" class="plus-form-item">
        <el-select
          v-model="row.state"
          clearable
          placeholder="请选择状态"
          class="plus-form-item-field"
        >
          <el-option
            v-for="item in stateEnums"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="角色" prop="roleList">
        <el-select
          v-model="row.roleList"
          multiple
          placeholder="请选择角色"
          clearable
        >
          <el-option
            v-for="item in roleList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" prop="email" class="plus-form-item">
        <el-input
          v-model="row.email"
          maxlength="50"
          show-word-limit
          placeholder="请填写邮箱"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender" class="plus-form-item">
        <el-select
          v-model="row.gender"
          clearable
          placeholder="请选择性别"
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
      <el-form-item label="备注" prop="remark" class="plus-form-item">
        <el-input
          v-model="row.remark"
          maxlength="255"
          show-word-limit
          placeholder="请填写备注"
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

import { enums } from "@/api/sys/common";
import { add, edit } from "./sysUser";
import { currentUserRolesWithOwn } from "../sysRole/sysRole";

const genderEnums = ref([]);
const stateEnums = ref([]);
//角色列表
const roleList = ref([]);
onMounted(() => {
  enums("Gender").then(res => {
    genderEnums.value = res.data;
  });
  enums("EnableState").then(res => {
    stateEnums.value = res.data;
  });
  currentUserRolesWithOwn().then(res => {
    roleList.value = res.data;
  });
});

const rules = reactive({
  account: [{ required: true, message: "请填写账号" }],
  // password: [
  //   { required: false, pattern: /^\w{6,16}$/, message: "密码格式错误" }
  // ],
  phone: [
    { required: false, pattern: /^\d{11}$/, message: "手机号码格式错误" }
  ],
  email: [
    {
      pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
      message: "邮箱格式错误"
    }
  ],
  state: [{ required: true, message: "请填写状态" }]
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
    drawerProps.value.title = "查看系统用户";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统用户" : "新增系统用户";
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
const disabledDateTimeFun = time => {
  return time.getTime() < Date.now() - 3600 * 1000 * 24;
};
defineExpose({
  acceptParams
});
</script>
