<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`表单代码预览`">
    <el-tabs model-value="api" type="card" class="demo-tabs">
      <el-tab-pane label="api" name="api">
        <Monaco v-model="drawerProps.apiText" id="apiCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="index" name="index">
        <Monaco v-model="drawerProps.indexText" id="indexCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="edit" name="edit">
        <Monaco v-model="drawerProps.editText" id="editCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="JavaPreview">
import { ref } from "vue";
import Monaco from "@/components/editor/Monaco.vue";

import { formCode, tableFormCode } from "@/api/sys/gen";

interface DrawerProps {
  tableName: string;
  apiText: string;
  indexText: string;
  editText: string;
}

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  tableName: "",
  apiText: "",
  indexText: "",
  editText: ""
});

// 接收父组件传过来的参数
const acceptParams = async (params: object): Promise<void> => {
  let res = await formCode(params, "api.ts");
  drawerProps.value.apiText = res.data;

  res = await formCode(params, "index.vue");
  drawerProps.value.indexText = res.data;

  res = await formCode(params, "edit.vue");
  drawerProps.value.editText = res.data;

  drawerVisible.value = true;
};
const acceptTableName = async (params: string): Promise<void> => {
  let res = await tableFormCode(params, "api.ts");
  drawerProps.value.apiText = res.data;

  res = await tableFormCode(params, "index.vue");
  drawerProps.value.indexText = res.data;

  res = await tableFormCode(params, "edit.vue");
  drawerProps.value.editText = res.data;

  drawerVisible.value = true;
};

defineExpose({
  acceptParams,
  acceptTableName
});
</script>
