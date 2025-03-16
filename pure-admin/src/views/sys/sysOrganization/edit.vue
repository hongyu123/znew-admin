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
      <el-form-item label="父部门id" prop="pid" class="plus-form-item">
        <el-input-number
          v-model="row.pid"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="row.type">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="部门名称" prop="name" class="plus-form-item">
        <el-input
          v-model="row.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="显示顺序" prop="sort" class="plus-form-item">
        <el-input-number
          v-model="row.sort"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="联系人" prop="linkName" class="plus-form-item">
        <el-input
          v-model="row.linkName"
          maxlength="20"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="linkPhone" class="plus-form-item">
        <el-input
          v-model="row.linkPhone"
          maxlength="20"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="联系邮箱" prop="linkEmail" class="plus-form-item">
        <el-input
          v-model="row.linkEmail"
          maxlength="50"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state" class="plus-form-item">
        <el-select
          v-model="row.state"
          clearable
          placeholder="请选择EnableState"
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
import { add, edit } from "./SysOrganization";
const stateEnums = ref([]);
onMounted(() => {
  enums("EnableState").then(res => {
    stateEnums.value = res.data;
  });
});

const rules = reactive({
  type: [{ required: true, message: "请选择类型" }],
  name: [{ required: true, message: "请填写部门名称" }],
  state: [{ required: true, message: "请选择状态" }]
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
    drawerProps.value.title = "查看组织机构";
  } else {
    drawerProps.value.title = rowData.id ? "编辑组织机构" : "新增组织机构";
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
