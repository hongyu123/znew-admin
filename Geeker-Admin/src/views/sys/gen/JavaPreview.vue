<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`Java代码预览`">
    <el-tabs model-value="entity" type="card" class="demo-tabs">
      <el-tab-pane label="entity" name="entity">
        <Monaco v-model="drawerProps.entityText" id="entityCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="dto" name="dto">
        <Monaco v-model="drawerProps.dtoText" id="dtoCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="mapper" name="mapper">
        <Monaco v-model="drawerProps.mapperText" id="mapperCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="mapperXml" name="mapperXml">
        <Monaco v-model="drawerProps.mapperXml" id="mapperXmlCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="service" name="service">
        <Monaco v-model="drawerProps.serviceText" id="serviceCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="impl" name="impl">
        <Monaco v-model="drawerProps.implText" id="implCodeEditBox" :readOnly="true"></Monaco>
      </el-tab-pane>
      <el-tab-pane label="controller" name="controller">
        <Monaco v-model="drawerProps.controllerText" id="controllerCodeEditBox" :readOnly="true"></Monaco>
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

import { javaCode } from "@/api/sys/gen";

interface DrawerProps {
  tableName: string;
  entityText: string;
  dtoText: string;
  mapperText: string;
  mapperXml: string;
  serviceText: string;
  implText: string;
  controllerText: string;
}

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  tableName: "",
  entityText: "1",
  dtoText: "2",
  mapperText: "3",
  mapperXml: "",
  serviceText: "4",
  implText: "5",
  controllerText: "6"
});

// 接收父组件传过来的参数
const acceptParams = (params: string) => {
  drawerProps.value.tableName = params;

  let codeCnt = 0;
  javaCode(drawerProps.value.tableName, "entity.java").then(res => {
    drawerProps.value.entityText = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "entityDTO.java").then(res => {
    drawerProps.value.dtoText = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "mapper.java").then(res => {
    drawerProps.value.mapperText = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "mapper.xml").then(res => {
    drawerProps.value.mapperXml = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "service.java").then(res => {
    drawerProps.value.serviceText = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "serviceimpl.java").then(res => {
    drawerProps.value.implText = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });

  javaCode(drawerProps.value.tableName, "controller.java").then(res => {
    drawerProps.value.controllerText = res.data;
    codeCnt++;
    if (codeCnt >= 7) {
      drawerVisible.value = true;
    }
  });
};

defineExpose({
  acceptParams
});
</script>
