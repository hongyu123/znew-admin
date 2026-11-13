<template>
  <el-drawer v-model="drawerProps.visible" :destroy-on-close="true" size="30%" :title="drawerProps.title">
    <el-form
      ref="sysDictionaryFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio v-for="item in EnableStateEnum" :value="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="row.leafFlag ? '字典值' : '字典类型'" prop="dictKey">
        <el-input v-model.trim="row.dictKey" maxlength="200" show-word-limit clearable />
      </el-form-item>
      <el-form-item :label="row.leafFlag ? '字典标签' : '字典名称'" prop="dictValue">
        <el-input v-model.trim="row.dictValue" maxlength="200" show-word-limit clearable />
      </el-form-item>
      <el-form-item label="排序值" prop="sort">
        <el-input-number v-model="row.sort" :min="0" clearable />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="row.remark" maxlength="200" show-word-limit clearable />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button v-show="!drawerProps.isView" type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup name="SysDictionaryDrawer">
import { ref, reactive } from "vue";
import { ElMessage } from "element-plus";

import { add, edit } from "./api";
import { EnableStateEnum } from "@/enums/commonEnum";

const rules = reactive({
  state: [{ required: true, message: "请选择状态", trigger: "change" }],
  dictKey: [{ required: true, message: "请填写字典类型", trigger: "change" }],
  dictValue: [{ required: true, message: "请填写字典名称", trigger: "change" }]
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
  drawerProps.value.refreshTable = getTableList;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看字典表";
  } else {
    drawerProps.value.title = rowData.id ? "编辑字典表" : "新增字典表";
  }
  drawerProps.value.visible = true;
};

// 提交数据（新增/编辑）
const sysDictionaryFormRef = ref();
const handleSubmit = () => {
  sysDictionaryFormRef.value.validate(async valid => {
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
