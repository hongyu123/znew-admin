<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`Java代码预览`">
    <el-tabs model-value="entity" type="card" class="demo-tabs">
      <el-tab-pane label="entity" name="entity">
        <codemirror
          v-model="drawerProps.entityText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
      <el-tab-pane label="mapper" name="mapper">
        <codemirror
          v-model="drawerProps.mapperText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
      <el-tab-pane label="mapperXml" name="mapperXml">
        <codemirror
          v-model="drawerProps.mapperXml"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
      <el-tab-pane label="service" name="service">
        <codemirror
          v-model="drawerProps.serviceText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
      <el-tab-pane label="controller" name="controller">
        <codemirror
          v-model="drawerProps.controllerText"
          :autofocus="true"
          :indent-with-tab="true"
          :tab-size="4"
          :extensions="extensions"
        />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="js" name="JavaPreview">
import { ref } from "vue";
import { Codemirror } from "vue-codemirror";
import { java } from "@codemirror/lang-java";
import { oneDark } from "@codemirror/theme-one-dark";
import { javaCode } from "./gen";

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref({
  tableName: "",
  entityText: "",
  mapperText: "",
  mapperXml: "",
  serviceText: "",
  controllerText: ""
});

const extensions = [java(), oneDark];

// 接收父组件传过来的参数
const acceptParams = params => {
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

defineExpose({
  acceptParams
});
</script>
<style>
.cm-scroller {
  font-family: "Source Code Pro", Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", "Microsoft Yahei" !important;
  font-size: 12px !important;
}
</style>
