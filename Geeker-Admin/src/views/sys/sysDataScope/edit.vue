<template>
  <el-drawer v-model="drawerProps.visible" :destroy-on-close="true" size="30%" :title="drawerProps.title">
    <el-form ref="formRef" label-width="110px" label-suffix=" :" :rules="rules" :model="row">
      <el-form-item label="配置类型" prop="configType">
        <el-radio-group v-model="row.configType" @change="handleConfigTypeChange">
          <el-radio v-for="item in DataScopeConfigTypeEnum" :key="item.value" :value="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 配置类型：用户 -->
      <el-form-item v-if="row.configType === 1" label="用户" prop="configId">
        <el-input :model-value="row.configName" placeholder="请选择用户" readonly>
          <template #append>
            <el-button @click="openUserSelect">选择</el-button>
          </template>
        </el-input>
      </el-form-item>
      <!-- 配置类型：组织机构 -->
      <el-form-item v-else-if="row.configType === 2" label="组织机构" prop="configId">
        <el-tree-select
          v-model="row.configId"
          :props="{ label: 'name', children: 'children' }"
          :data="orgTreeData"
          :render-after-expand="false"
          check-strictly
          clearable
          default-expand-all
          node-key="id"
          placeholder="请选择组织机构"
          style="width: 100%"
          @change="handleOrgChange"
        />
      </el-form-item>
      <!-- 配置类型：角色（当前拥有的角色） -->
      <el-form-item v-else-if="row.configType === 3" label="角色" prop="configId">
        <el-select v-model="row.configId" placeholder="请选择角色" clearable style="width: 100%" @change="handleRoleChange">
          <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="数据key" prop="dataKey">
        <el-input v-model.trim="row.dataKey" maxlength="100" show-word-limit placeholder="请填写数据key" clearable />
      </el-form-item>
      <el-form-item label="数据权限" prop="dataScope">
        <el-select v-model="row.dataScope" clearable>
          <el-option v-for="item in dataScopeEnum" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <!-- 数据权限类型：自定义部门 -->
      <el-form-item v-if="row.dataScope === 'Custom'" label="自定义部门" prop="customIds">
        <el-tree-select
          v-model="row.customIds"
          :props="{ label: 'name', children: 'children' }"
          :data="orgTreeData"
          :render-after-expand="false"
          multiple
          show-checkbox
          check-strictly
          clearable
          default-expand-all
          node-key="id"
          placeholder="请选择部门"
          style="width: 100%"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>

  <SysUserSelect ref="userSelectRef" />
</template>

<script setup lang="js" name="SysDataScopeDrawer">
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";

import SysUserSelect from "../sysUser/select.vue";
import { tree as reqOrgTree } from "../sysOrganization/api";
import { currentUserRolesWithOwn } from "../sysRole/api";
import { enums } from "@/api/modules/common";
import { DataScopeConfigTypeEnum } from "@/enums/commonEnum";
import { add, edit } from "./api";

// 抽屉状态
const drawerProps = ref({
  title: "",
  visible: false
});
const row = ref({});

// 组织机构树
const orgTreeData = ref([]);
// 当前用户拥有的角色
const roleList = ref([]);
// 数据权限枚举
const dataScopeEnum = ref([]);
// 用户选择组件
const userSelectRef = ref();

const rules = reactive({
  configType: [{ required: true, message: "请选择配置类型", trigger: "change" }],
  configId: [{ required: true, message: "请选择配置对象", trigger: "change" }],
  dataKey: [{ required: true, message: "请填写数据key", trigger: "change" }],
  dataScope: [{ required: true, message: "请选择数据权限", trigger: "change" }],
  customIds: [{ type: "array", required: true, message: "请选择自定义部门", trigger: "change" }]
});

// 接收父组件传过来的参数
const acceptParams = (rowData, refreshTable) => {
  row.value = { ...rowData, customIds: rowData.customIds || [] };
  drawerProps.value.refreshTable = refreshTable;
  drawerProps.value.title = rowData.id ? "编辑数据权限配置" : "新增数据权限配置";
  drawerProps.value.visible = true;
  loadOptions();
};

// 加载下拉/树数据
const loadOptions = () => {
  reqOrgTree().then(res => {
    orgTreeData.value = res.data || [];
  });
  currentUserRolesWithOwn().then(res => {
    roleList.value = res.data || [];
  });
  enums("DataScope").then(res => {
    dataScopeEnum.value = res.data || [];
  });
};

// 配置类型切换时清空已选配置对象
const formRef = ref();
const handleConfigTypeChange = () => {
  row.value.configId = undefined;
  row.value.configName = "";
  formRef.value?.clearValidate("configId");
};

// 组织机构选择后同步名称
const findOrgName = (list, id) => {
  for (const item of list) {
    if (item.id == id) return item.name;
    if (item.children && item.children.length) {
      const name = findOrgName(item.children, id);
      if (name) return name;
    }
  }
  return "";
};
const handleOrgChange = id => {
  row.value.configName = id ? findOrgName(orgTreeData.value, id) : "";
};

// 角色选择后同步名称
const handleRoleChange = id => {
  const role = roleList.value.find(item => item.id == id);
  row.value.configName = role ? role.name : "";
};

// 选择用户（单选，取选择结果第一个）
const openUserSelect = async () => {
  const userList = await userSelectRef.value.open({ title: "选择用户" });
  if (!userList || !userList.length) return;
  const user = userList[0];
  row.value.configId = user.id;
  row.value.configName = user.nickname || user.account;
  formRef.value?.validateField("configId");
};

// 数据权限类型切换
const handleDataScopeChange = value => {
  if (value !== "Custom") row.value.customIds = [];
  formRef.value?.clearValidate("customIds");
};

// 提交数据（新增/编辑）
const handleSubmit = () => {
  formRef.value.validate(async valid => {
    if (!valid) return;
    // 非自定义部门时清空自定义部门ids
    if (row.value.dataScope !== "Custom") row.value.customIds = [];
    try {
      const api = row.value.id ? edit : add;
      const { message } = await api(row.value);
      ElMessage.success({ message });
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
