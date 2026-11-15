<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`表单代码预览`">
    <el-tabs model-value="api" type="card" class="demo-tabs">
      <el-tab-pane label="api" name="api">
        <codemirror
          v-model="drawerProps.apiText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="js_extensions"
        />
      </el-tab-pane>
      <el-tab-pane label="index" name="index">
        <codemirror
          v-model="drawerProps.indexText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
      <el-tab-pane label="edit" name="edit">
        <codemirror
          v-model="drawerProps.editText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button v-show="drawerProps.genTable" type="primary" @click="handleSaveGenFormRecord">保存</el-button>
      <el-button @click="drawerVisible = false">取消</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="js" name="FormPreview">
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { Codemirror } from "vue-codemirror";
import { vue } from "@codemirror/lang-vue";
import { javascript } from "@codemirror/lang-javascript";
import { oneDark } from "@codemirror/theme-one-dark";

import { formCode, tableFormCode, saveGenFormRecord } from "./gen";

const extensions = [vue(), oneDark];
const js_extensions = [javascript(), oneDark];

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref({
  apiText: "",
  indexText: "",
  editText: ""
});

// 接收父组件传过来的参数
const acceptParams = params => {
  drawerProps.value.genTable = params;
  drawerVisible.value = true;
  let codeCnt = 0;
  formCode(params, "api.js").then(res => {
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
const acceptTableName = params => {
  let codeCnt = 0;
  drawerVisible.value = true;
  tableFormCode(params, "api.js").then(res => {
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
<style>
.cm-scroller {
  font-family: "Source Code Pro", Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", "Microsoft Yahei" !important;
  font-size: 12px !important;
}
</style>
