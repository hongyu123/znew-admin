<template>
  <el-drawer
    v-model="drawerVisible"
    :destroy-on-close="true"
    size="50%"
    :title="`表单代码预览`"
  >
    <el-tabs
      model-value="api"
      type="card"
      class="demo-tabs"
      @tab-change="handleTabChange"
    >
      <el-tab-pane label="api" name="api">
        <Codemirror
          ref="cmApiRef"
          v-model:value="drawerProps.apiText"
          width="100%"
          :options="{
            mode: 'javascript',
            theme: 'material-darker',
            readOnly: true
          }"
          @ready="onCmReady"
        />
      </el-tab-pane>
      <el-tab-pane label="index" name="index">
        <Codemirror
          ref="cmIndexRef"
          v-model:value="drawerProps.indexText"
          width="100%"
          :options="{
            mode: 'javascript',
            theme: 'material-darker',
            readOnly: true
          }"
        />
      </el-tab-pane>
      <el-tab-pane label="edit" name="edit">
        <Codemirror
          ref="cmEditRef"
          v-model:value="drawerProps.editText"
          width="100%"
          :options="{
            mode: 'javascript',
            theme: 'material-darker',
            readOnly: true
          }"
        />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button
        v-show="drawerProps.genTable"
        type="primary"
        @click="handleSaveGenFormRecord"
        >保存</el-button
      >
      <el-button @click="drawerVisible = false">取消</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="FormPreview">
import { ref } from "vue";
import { ElMessage } from "element-plus";

import "codemirror/theme/material-darker.css";
import "codemirror/addon/hint/show-hint.css";
import "codemirror/addon/hint/show-hint";
import "codemirror/addon/hint/javascript-hint.js";
import "codemirror/mode/javascript/javascript.js";
import Codemirror from "codemirror-editor-vue3";

import { formCode, tableFormCode, saveGenFormRecord } from "./gen";

interface DrawerProps {
  apiText: string;
  indexText: string;
  editText: string;
  genTable?: any;
}

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  apiText: "",
  indexText: "",
  editText: ""
});

// 接收父组件传过来的参数
const acceptParams = (params: object) => {
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
const acceptTableName = (params: string) => {
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

const cmApiRef = ref();
const cmIndexRef = ref();
const cmEditRef = ref();
const onCmReady = cm => {
  cmApiRef.value.refresh(); //刷新解决行号位置错乱问题
};
const handleTabChange = tabName => {
  if (tabName == "index") {
    cmIndexRef.value.refresh();
  } else if (tabName == "edit") {
    cmEditRef.value.refresh();
  }
};
defineExpose({
  acceptParams,
  acceptTableName
});
</script>
