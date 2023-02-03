<template>
  <el-form label-width="200px">
    <span>{{ genTable.tableName }}</span>
    <draggable
      class="card grid-container"
      v-model="genTable.columnList"
      item-key="columnName"
      animation="300"
      chosenClass="chosen"
      forceFallback="true"
    >
      <template #item="{ element }">
        <el-form-item :label="element.label" :prop="element.property">
          <el-select v-model="element.formType" placeholder="please select your zone">
            <el-option label="文本框" value="input" />
            <el-option label="文本域" value="textarea" />
            <el-option label="富文本" value="richtext" />
            <el-option label="手机号码" value="phone" />
            <el-option label="数字框" value="number" />
            <el-option label="下拉框" value="select" />
            <el-option label="单选框" value="radio" />
            <el-option label="多选框" value="checkbox" />
            <el-option label="日期" value="date" />
            <el-option label="日期时间" value="datetime" />
            <el-option label="图片上传" value="picture" />
            <el-option label="多图上传" value="pictures" />
            <el-option label="文件上传" value="file" />
            <el-option label="文件输入" value="fileInput" />
            <el-option label="视频" value="video" />
            <el-option label="地图" value="map" />
          </el-select>
          <el-switch v-model="element.listFlag" active-text="列表" :active-value="1" :inactive-value="0"></el-switch>
          <el-switch v-model="element.searchFlag" active-text="查询" :active-value="1" :inactive-value="0"></el-switch>
          <el-switch v-model="element.required" active-text="必填" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </template>
    </draggable>
    <el-form-item>
      <el-button type="primary" icon="View" @click="preview">预览</el-button>
      <el-button type="primary" @click="handleGenFormToPath">配置路径生成</el-button>
      <el-button type="primary" @click="handleGenFormToProject">项目路径生成</el-button>
    </el-form-item>

    <FormPreview ref="formPreviewRef" />
  </el-form>
</template>

<script setup lang="ts" name="Draggable">
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useRoute } from "vue-router";
import draggable from "vuedraggable/src/vuedraggable";
import FormPreview from "@/views/sys/gen/FormPreview.vue";

import { formInfo, detail, genFormToPath, genFormToProject } from "@/api/sys/gen";

const route = useRoute();
let genTable = ref({ tableName: "", columnList: [] });
onMounted(() => {
  if (route.query.id) {
    detail(route.query.id).then(res => {
      genTable.value = res.data;
    });
  } else if (route.query.tableName) {
    formInfo({ tableName: route.query.tableName }).then(res => {
      genTable.value = res.data;
    });
  }
});

const handleGenFormToPath = () => {
  genFormToPath(genTable.value).then(res => {
    ElMessage({
      type: "success",
      message: res.message
    });
  });
};
const handleGenFormToProject = () => {
  genFormToProject(genTable.value).then(res => {
    ElMessage({
      type: "success",
      message: res.message
    });
  });
};

const formPreviewRef = ref();
const preview = () => {
  formPreviewRef.value.acceptParams(genTable.value);
};
</script>
