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
const acceptParams = async (params: string): Promise<void> => {
  drawerProps.value.tableName = params;
  let res = await javaCode(drawerProps.value.tableName, "entity.java");
  drawerProps.value.entityText = res.data;

  res = await javaCode(drawerProps.value.tableName, "entityDTO.java");
  drawerProps.value.dtoText = res.data;

  res = await javaCode(drawerProps.value.tableName, "mapper.java");
  drawerProps.value.mapperText = res.data;

  res = await javaCode(drawerProps.value.tableName, "mapper.xml");
  drawerProps.value.mapperXml = res.data;

  res = await javaCode(drawerProps.value.tableName, "service.java");
  drawerProps.value.serviceText = res.data;

  res = await javaCode(drawerProps.value.tableName, "serviceimpl.java");
  drawerProps.value.implText = res.data;

  res = await javaCode(drawerProps.value.tableName, "controller.java");
  drawerProps.value.controllerText = res.data;

  drawerVisible.value = true;
};

defineExpose({
  acceptParams
});
</script>
