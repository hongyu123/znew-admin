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
      <el-form-item
        :label="row.leafFlag ? '字典值' : '字典类型'"
        prop="dictKey"
        class="plus-form-item"
      >
        <el-input
          v-model="row.dictKey"
          maxlength="200"
          show-word-limit
          placeholder="请填写"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        :label="row.leafFlag ? '字典标签' : '字典名称'"
        prop="dictValue"
        class="plus-form-item"
      >
        <el-input
          v-model="row.dictValue"
          maxlength="200"
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
      <el-form-item label="排序值" prop="sort" class="plus-form-item">
        <el-input-number
          v-model="row.sort"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark" class="plus-form-item">
        <el-input
          v-model="row.remark"
          maxlength="200"
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

import { enums } from "@/api/sys/common";
import { add, edit } from "./sysDictionary";
const stateEnums = ref([]);
onMounted(() => {
  enums("EnableState").then(res => {
    stateEnums.value = res.data;
  });
});

const rules = reactive({
  state: [{ required: true, message: "请选择状态" }],
  dictKey: [{ required: true, message: "请填写" }],
  dictValue: [{ required: true, message: "请填写" }]
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
  const title = rowData.leafFlag ? "字典数据" : "字典分类";
  row.value = rowData;
  drawerProps.value.getTableList = getTableList;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = `查看${title}`;
  } else {
    drawerProps.value.title = rowData.id ? `编辑${title}` : `新增${title}`;
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
