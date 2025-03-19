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
      <el-form-item label="角色名" prop="name" class="plus-form-item">
        <el-input
          v-model="row.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写角色名"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="角色编码" prop="code" class="plus-form-item">
        <el-input
          v-model="row.code"
          maxlength="50"
          show-word-limit
          placeholder="请填写角色编码"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="排序" prop="sort" class="plus-form-item">
        <el-input-number
          v-model="row.sort"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否系统内置" prop="systemFlag">
        <el-radio-group v-model="row.systemFlag">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
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
      <el-form-item label="创建账号" prop="createUser" class="plus-form-item">
        <el-input
          v-model="row.createUser"
          maxlength="50"
          show-word-limit
          placeholder="请填写创建账号"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime" class="plus-form-item">
        <el-date-picker
          v-model="row.createTime"
          type="datetime"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择创建时间"
          class="plus-form-item-field"
          :disabledDate="disabledDateTimeFun"
        />
      </el-form-item>
      <el-form-item label="更新账号" prop="updateUser" class="plus-form-item">
        <el-input
          v-model="row.updateUser"
          maxlength="50"
          show-word-limit
          placeholder="请填写更新账号"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime" class="plus-form-item">
        <el-date-picker
          v-model="row.updateTime"
          type="datetime"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择更新时间"
          class="plus-form-item-field"
          :disabledDate="disabledDateTimeFun"
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

import { enums } from "@/api/sys/common";
import { add, edit } from "./sysRole";
onMounted(() => {});

const rules = reactive({
  name: [{ required: true, message: "请填写角色名" }],
  state: [{ required: true, message: "请选择状态" }],
  createUser: [{ required: true, message: "请填写创建账号" }],
  createTime: [{ required: true, message: "请填写创建时间" }]
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
    drawerProps.value.title = "查看系统角色";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统角色" : "新增系统角色";
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
