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
      <el-form-item label="所属部门" prop="orgId">
        <el-tree-select
          v-model="row.orgId"
          :props="{ label: 'name', children: 'children' }"
          :data="orgTreeData"
          :render-after-expand="false"
          check-strictly
          clearable
          default-expand-all
          node-key="id"
          placeholder="请选择所属部门"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input
          v-model.trim="row.account"
          maxlength="50"
          show-word-limit
          placeholder="请填写账号"
          clearable
          :disabled="!!row.id"
        ></el-input>
      </el-form-item>
      <el-form-item v-if="!row.id" label="密码" prop="password">
        <el-input v-model="row.password" type="password" placeholder="请填写密码" clearable></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model.trim="row.phone" maxlength="11" show-word-limit placeholder="请填写手机号码" clearable></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model.trim="row.nickname" maxlength="50" show-word-limit placeholder="请填写昵称" clearable></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:image-url="row.avatar" :api="upload" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model.trim="row.email" maxlength="50" show-word-limit placeholder="请填写邮箱" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="row.gender">
          <el-radio value="Male">男</el-radio>
          <el-radio value="Female">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio value="Enable">启用</el-radio>
          <el-radio value="Disable">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="角色" prop="roleList">
        <el-select multiple v-model="row.roleList" placeholder="请选择角色" clearable>
          <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="row.remark" maxlength="255" show-word-limit placeholder="请填写备注" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="js" name="SysUserDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { upload } from "@/api/modules/common";
import UploadImg from "@/components/Upload/Img.vue";
import { tree as reqOrgTree } from "../sysOrganization/api";
import { page as reqRoleList } from "../sysRole/api";
import { add, edit } from "./api";

const row = ref({});
const rules = reactive({
  orgId: [{ required: true, message: "请选择部门", trigger: "change" }],
  account: [{ required: true, message: "请填写账号", trigger: "change" }],
  password: [{ required: row.value.id ? true : false, pattern: /^\w{6,16}$/, message: "密码格式错误", trigger: "change" }],
  phone: [{ required: false, pattern: /^\d{11}$/, message: "手机号码格式错误", trigger: "change" }],
  email: [{ pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: "邮箱格式错误", trigger: "change" }],
  state: [{ required: true, message: "请填写状态", trigger: "change" }],
  roleList: [{ required: true, message: "请填写角色", trigger: "change" }]
});

// drawer框状态
const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
});
// 部门树数据
const orgTreeData = ref([]);
//角色列表
const roleList = ref([]);

// 接收父组件传过来的参数
const acceptParams = (rowData, refreshTable, isView) => {
  row.value = rowData;
  drawerProps.value.refreshTable = refreshTable;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看系统用户";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统用户" : "新增系统用户";
  }
  drawerProps.value.visible = true;

  reqOrgTree().then(res => {
    orgTreeData.value = res.data;
  });
  reqRoleList({ state: "Enable" }).then(res => {
    roleList.value = res.data;
  });
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
      const api = row.value && row.value.id ? edit : add;
      const { message } = await api(row.value);
      ElMessage.success({ message: message });
      drawerProps.value.refreshTable();
      drawerProps.value.visible = false;
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({
  acceptParams
});
</script>
