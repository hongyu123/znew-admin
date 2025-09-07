<template>
  <div class="main">
    <PlusSearch
      v-show="showSearch"
      v-model="queryParams"
      class="bg-bg_color px-8 py-[12px]"
      :columns="searchColumns"
      :show-number="5"
      label-width="80"
      label-position="right"
      :colProps="{ xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }"
      :prevent="true"
      @change="handleSearchChange"
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="代码生成"
      :columns="tableColumns"
      @refresh="handleSearch"
      @showSearch="onShowSearch"
    >
      <template #buttons>
        <el-button
          v-perms="['gen:gen']"
          type="primary"
          :disabled="selectedData.length <= 0"
          @click="handleBatchGenToPath()"
        >
          配置路径生成
        </el-button>
        <el-button
          v-perms="['gen:genToProject']"
          type="primary"
          :disabled="selectedData.length <= 0"
          @click="handleBatchGenToProject()"
        >
          项目路径生成
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          ref="tableRef"
          row-key="id"
          align-whole="center"
          table-layout="auto"
          :loading="loading"
          :size="size"
          adaptive
          :adaptiveConfig="{ offsetBottom: 108 }"
          :data="dataList"
          :columns="dynamicColumns"
          :pagination="{
            background: true,
            total,
            currentPage: queryParams.pageNumber,
            pageSize: queryParams.pageSize,
            size
          }"
          :header-cell-style="{
            background: 'var(--el-fill-color-light)',
            color: 'var(--el-text-color-primary)'
          }"
          @sort-change="handleSortChange"
          @selection-change="handleSelectionChange"
          @page-size-change="handleSizeChange"
          @page-current-change="handleCurrentChange"
        >
          <template #operation="{ row }">
            <el-button
              v-perms="['gen:genView']"
              type="primary"
              :icon="useRenderIcon(EpView)"
              link
              @click="preview(row)"
            >
              预览
            </el-button>
            <el-button
              v-perms="['gen:gen']"
              type="primary"
              link
              @click="handleGenToPath(row)"
            >
              配置路径生成
            </el-button>
            <el-button
              v-perms="['gen:genToProject']"
              type="primary"
              link
              @click="handleGenToProject(row)"
            >
              项目路径生成
            </el-button>
            <el-button
              v-perms="['gen:gen']"
              type="primary"
              link
              @click="formGen(row)"
            >
              表单生成
            </el-button>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
    <JavaPreview ref="javaPreviewRef" />
  </div>
</template>

<script setup lang="jsx">
import "plus-pro-components/es/components/search/style/css";
import EpView from "@iconify-icons/ep/view";

import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";

import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { useHandleData } from "@/hooks/useHandleData";
import { useQueryTable } from "@/hooks/useQueryTable";
import { useMultiTagsStoreHook } from "@/store/modules/multiTags";
import JavaPreview from "./JavaPreview.vue";
import {
  tablePage,
  genToPath,
  genToProject,
  batchGenToPath,
  batchGenToProject
} from "./gen";

onMounted(() => {
  getTableList();
});

const searchColumns = reactive([
  {
    label: "表名",
    prop: "tableName"
  }
]);

/** 搜索表单字段变化处理 */
const handleSearchChange = (values, column) => {};

const tableRef = ref();
const tableColumns = [
  {
    label: "勾选列",
    type: "selection",
    fixed: "left"
  },
  { prop: "tableName", label: "表名" },
  { prop: "tableComment", label: "注释" },
  {
    label: "操作",
    fixed: "right",
    slot: "operation",
    width: 400
  }
];

/** 表格获取数据 */
const getTableList = async () => {
  loading.value = true;
  const res = await tablePage(queryParams.value);
  total.value = res.total;
  dataList.value = res.data;
  loading.value = false;
};

//表单生成
const router = useRouter();
const formGen = row => {
  const parameter = { tableName: row.tableName };
  useMultiTagsStoreHook().handleTags("push", {
    path: `/sys/formGen`,
    name: "formGen",
    query: parameter,
    meta: {
      title: "表单生成"
    }
  });
  router.push({ name: "formGen", query: parameter });
};

//预览
const javaPreviewRef = ref();
const preview = row => {
  javaPreviewRef.value.acceptParams(row.tableName);
};

//配置路径生成
const handleGenToPath = async row => {
  const res = await genToPath({ tableName: row.tableName });
  ElMessage({
    type: "success",
    message: res.message
  });
};

//项目路径生成
const handleGenToProject = async row => {
  const res = await genToProject({ tableName: row.tableName });
  ElMessage({
    type: "success",
    message: res.message
  });
};

// 批量生成
const handleBatchGenToPath = async () => {
  const ids = selectedData.value.map(item => item.tableName);
  await useHandleData(batchGenToPath, ids, "配置路径批量生成");
};
const handleBatchGenToProject = async () => {
  const ids = selectedData.value.map(item => item.tableName);
  await useHandleData(batchGenToProject, ids, "项目路径批量生成");
};

const {
  queryParams,
  showSearch,
  handleSearch,
  handleReset,
  onShowSearch,
  loading,
  total,
  dataList,
  selectedData,
  handleSortChange,
  handleCurrentChange,
  handleSizeChange,
  handleSelectionChange
} = useQueryTable(tableRef, getTableList);
</script>
