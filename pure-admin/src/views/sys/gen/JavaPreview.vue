<template>
  <el-drawer
    v-model="drawerVisible"
    :destroy-on-close="true"
    size="50%"
    :title="`Java代码预览`"
  >
    <el-tabs
      model-value="entity"
      type="card"
      class="demo-tabs"
      @tab-change="handleTabChange"
    >
      <el-tab-pane label="entity" name="entity">
        <Codemirror
          ref="cmEntityRef"
          v-model:value="drawerProps.entityText"
          width="100%"
          :options="{
            mode: 'javascript',
            theme: 'material-darker',
            readOnly: true
          }"
          @ready="onCmReady"
        />
      </el-tab-pane>
      <el-tab-pane label="mapper" name="mapper">
        <Codemirror
          ref="cmMapperRef"
          v-model:value="drawerProps.mapperText"
          width="100%"
          :options="{
            mode: 'javascript',
            theme: 'material-darker',
            readOnly: true
          }"
        />
      </el-tab-pane>
      <el-tab-pane label="mapperXml" name="mapperXml">
        <Codemirror
          ref="cmMapperXmlRef"
          v-model:value="drawerProps.mapperXml"
          width="100%"
          :options="{
            mode: 'xml',
            theme: 'material-darker',
            readOnly: true
          }"
        />
      </el-tab-pane>
      <el-tab-pane label="service" name="service">
        <Codemirror
          ref="cmServiceRef"
          v-model:value="drawerProps.serviceText"
          width="100%"
          :options="{
            mode: 'javascript',
            theme: 'material-darker',
            readOnly: true
          }"
        />
      </el-tab-pane>
      <el-tab-pane label="controller" name="controller">
        <Codemirror
          ref="cmControllerRef"
          v-model:value="drawerProps.controllerText"
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
      <el-button @click="drawerVisible = false">取消</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="JavaPreview">
import { ref } from "vue";
import "codemirror/theme/material-darker.css";
import "codemirror/addon/hint/show-hint.css";
import "codemirror/addon/hint/show-hint";
import "codemirror/addon/hint/javascript-hint.js";
import "codemirror/mode/javascript/javascript.js";
import "codemirror/mode/xml/xml";
import Codemirror from "codemirror-editor-vue3";

import { javaCode } from "./gen";

interface DrawerProps {
  tableName: string;
  entityText: string;
  mapperText: string;
  mapperXml: string;
  serviceText: string;
  controllerText: string;
}

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  tableName: "",
  entityText: "",
  mapperText: "",
  mapperXml: "",
  serviceText: "",
  controllerText: ""
});

// 接收父组件传过来的参数
const acceptParams = (params: string) => {
  drawerProps.value.tableName = params;

  let codeCnt = 0;
  javaCode(drawerProps.value.tableName, "entity.java").then(res => {
    drawerProps.value.entityText = res.data;
    codeCnt++;
    if (codeCnt >= 5) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "mapper.java").then(res => {
    drawerProps.value.mapperText = res.data;
    codeCnt++;
    if (codeCnt >= 5) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "mapper.xml").then(res => {
    drawerProps.value.mapperXml = res.data;
    codeCnt++;
    if (codeCnt >= 5) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "service.java").then(res => {
    drawerProps.value.serviceText = res.data;
    codeCnt++;
    if (codeCnt >= 5) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "controller.java").then(res => {
    drawerProps.value.controllerText = res.data;
    codeCnt++;
    if (codeCnt >= 5) {
      drawerVisible.value = true;
    }
  });
};

const cmEntityRef = ref();
const cmMapperRef = ref();
const cmMapperXmlRef = ref();
const cmServiceRef = ref();
const cmControllerRef = ref();
const onCmReady = cm => {
  cmEntityRef.value.refresh(); //刷新解决行号位置错乱问题
};
const handleTabChange = tabName => {
  if (tabName == "mapper") {
    cmMapperRef.value.refresh();
  } else if (tabName == "mapperXml") {
    cmMapperXmlRef.value.refresh();
  } else if (tabName == "service") {
    cmServiceRef.value.refresh();
  } else if (tabName == "controller") {
    cmControllerRef.value.refresh();
  }
};

defineExpose({
  acceptParams
});
</script>
