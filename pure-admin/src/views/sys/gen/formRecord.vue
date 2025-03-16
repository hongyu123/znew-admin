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
      @change="handleSearchChange"
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="表单生成记录"
      :columns="tableColumns"
      @refresh="handleSearch"
      @showSearch="onShowSearch"
    >
      <template #buttons />
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
            <el-button type="primary" icon="View" link @click="preview(row)">
              预览
            </el-button>
            <el-button type="primary" link @click="formGen(row)">
              表单生成
            </el-button>
          </template>
        </pure-table>
      </template>
    </PureTableBar>

    <FormPreview ref="formPreviewRef" />
  </div>
</template>

<script setup lang="jsx">
import "plus-pro-components/es/components/search/style/css";

import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";
import { useQueryTable } from "@/hooks/useQueryTable";

import FormPreview from "./FormPreview.vue";
import { page } from "./gen";

onMounted(() => {
  getTableList();
});

const searchColumns = reactive([
  {
    label: "表名",
    prop: "params.tableName_like"
  },
  {
    label: "表注释",
    prop: "params.tableRemark_like"
  }
]);

const inputColumns = ["params.tableName_like", "params.tableRemark_like"];
/** 搜索表单字段变化处理 */
const handleSearchChange = (values, column) => {
  if (inputColumns.indexOf(column.prop) < 0) {
    getTableList();
  }
};

const tableRef = ref();
const tableColumns = [
  {
    prop: "tableName",
    label: "表名"
  },
  {
    prop: "tableRemark",
    label: "表注释"
  },
  {
    label: "操作",
    fixed: "right",
    slot: "operation"
  }
];

/** 表格获取数据 */
const getTableList = async () => {
  loading.value = true;
  const res = await page(queryParams.value);
  total.value = res.total;
  dataList.value = res.data;
  loading.value = false;
};

//预览
const formPreviewRef = ref();
const preview = row => {
  formPreviewRef.value.acceptTableName(row.id);
};

//表单生成
const router = useRouter();
const formGen = row => {
  router.push(`/sys/gen/form?id=${row.id}`);
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
  handleSortChange,
  handleCurrentChange,
  handleSizeChange,
  handleSelectionChange
} = useQueryTable(tableRef, getTableList);
</script>
