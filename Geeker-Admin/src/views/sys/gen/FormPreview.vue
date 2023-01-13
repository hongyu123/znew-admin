<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`表单代码预览`">
    <el-tabs model-value="api" type="card" class="demo-tabs">
      <el-tab-pane label="api" name="api">
        <Monaco v-model="drawerProps.apiText" id="apiCodeEditBox" :readOnly="true" language="typescript"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="index" name="index">
        <Monaco v-model="drawerProps.indexText" id="indexCodeEditBox" :readOnly="true" language="html"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="edit" name="edit">
        <Monaco v-model="drawerProps.editText" id="editCodeEditBox" :readOnly="true" language="html"></Monaco>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button type="primary" v-show="drawerProps.genTable" @click="handleSaveGenFormRecord">保存</el-button>
      <el-button @click="drawerVisible = false">取消</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="JavaPreview">
import { ref } from "vue";
import { ElMessage } from "element-plus";
import Monaco from "@/components/editor/Monaco.vue";

import { formCode, tableFormCode, saveGenFormRecord } from "@/api/sys/gen";

interface DrawerProps {
  tableName: string;
  apiText: string;
  indexText: string;
  editText: string;
  genTable?: any;
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
const acceptParams = (params: object) => {
  drawerProps.value.genTable = params;
  let codeCnt = 0;
  formCode(params, "api.ts").then(res => {
    drawerProps.value.apiText = res.data;
    codeCnt++;
    if (codeCnt >= 3) {
      drawerVisible.value = true;
    }
  });
  formCode(params, "index.vue").then(res => {
    drawerProps.value.indexText = res.data;
    codeCnt++;
    if (codeCnt >= 3) {
      drawerVisible.value = true;
    }
  });
  formCode(params, "edit.vue").then(res => {
    drawerProps.value.editText = res.data;
    codeCnt++;
    if (codeCnt >= 3) {
      drawerVisible.value = true;
    }
  });
};
const acceptTableName = (params: string) => {
  let codeCnt = 0;
  tableFormCode(params, "api.ts").then(res => {
    drawerProps.value.apiText = res.data;
    codeCnt++;
    if (codeCnt >= 3) {
      drawerVisible.value = true;
    }
  });
  tableFormCode(params, "index.vue").then(res => {
    drawerProps.value.indexText = res.data;
    codeCnt++;
    if (codeCnt >= 3) {
      drawerVisible.value = true;
    }
  });
  tableFormCode(params, "edit.vue").then(res => {
    drawerProps.value.editText = res.data;
    codeCnt++;
    if (codeCnt >= 3) {
      drawerVisible.value = true;
    }
  });
};

const handleSaveGenFormRecord = () => {
  saveGenFormRecord(drawerProps.value.genTable).then(res => {
    ElMessage.success(res.message);
  });
};

defineExpose({
  acceptParams,
  acceptTableName
});
</script>
