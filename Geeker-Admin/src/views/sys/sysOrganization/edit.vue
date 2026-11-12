<template>
  <el-drawer v-model="drawerProps.visible" :destroy-on-close="true" size="30%" :title="drawerProps.title">
    <el-form
      ref="sysOrganizationFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="父部门" prop="pid">
        <el-tree-select
          v-model="row.pid"
          :props="{ label: 'name', children: 'children' }"
          :data="treeSelectData"
          :render-after-expand="false"
          check-strictly
          node-key="id"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="row.type">
          <el-radio :value="1">公司</el-radio>
          <el-radio :value="2">部门</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="row.type == 1 ? '公司名称' : '部门名称'" prop="name">
        <el-input v-model.trim="row.name" maxlength="50" show-word-limit clearable />
      </el-form-item>
      <el-form-item label="显示顺序" prop="sort">
        <el-input-number v-model="row.sort" :min="0" clearable />
      </el-form-item>
      <el-form-item label="联系人" prop="linkName">
        <el-input v-model.trim="row.linkName" maxlength="20" show-word-limit clearable />
      </el-form-item>
      <el-form-item label="联系电话" prop="linkPhone">
        <el-input v-model.trim="row.linkPhone" type="tel" clearable />
      </el-form-item>
      <el-form-item label="联系邮箱" prop="linkEmail">
        <el-input v-model.trim="row.linkEmail" type="email" clearable />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio v-for="item in EnableStateEnum" :value="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button v-show="!drawerProps.isView" type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup name="OrganizationDrawer">
import { ref, reactive } from "vue";
import { ElMessage } from "element-plus";

import { add, edit, tree } from "./api";
import { EnableStateEnum } from "@/enums/commonEnum";

const rules = reactive({
  pid: [{ required: true, message: "请选择父部门", trigger: "change" }],
  type: [{ required: true, message: "请选择类型", trigger: "change" }],
  name: [{ required: true, message: "请填写部门名称", trigger: "change" }],
  linkPhone: [{ required: false, pattern: /^\d{11}$/, message: "联系电话格式错误", trigger: "change" }],
  state: [{ required: true, message: "请选择状态", trigger: "change" }]
});

const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
  //getTableList
});
const row = ref({});
const treeSelectData = ref([]);

// 接收父组件传过来的参数
const acceptParams = (rowData, getTableList, isView) => {
  row.value = rowData;
  drawerProps.value.refreshTable = getTableList;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看组织机构";
  } else {
    drawerProps.value.title = rowData.id ? "编辑组织机构" : "新增组织机构";
  }
  drawerProps.value.visible = true;
  tree("Enable").then(res => {
    treeSelectData.value = res.data;
  });
};

// 提交数据（新增/编辑）
const sysOrganizationFormRef = ref();
const handleSubmit = () => {
  sysOrganizationFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
      const api = row.value && row.value.id ? edit : add;
      await api(row.value);
      ElMessage.success({ message: `${drawerProps.value.title}成功！` });
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
