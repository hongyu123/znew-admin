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
      :colProps="colProps"
      :prevent="true"
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="admin日志"
      :columns="tableColumns"
      @refresh="handleSearch"
      @showSearch="onShowSearch"
    >
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          ref="tableRef"
          row-key="id"
          align-whole="center"
          table-layout="auto"
          :loading="loading"
          :size="size"
          adaptive
          :adaptiveConfig="adaptiveConfig"
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
        />
      </template>
    </PureTableBar>
  </div>
</template>

<script setup lang="jsx">
import "plus-pro-components/es/components/search/style/css";

import { ref, reactive, onMounted } from "vue";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { useHandleData } from "@/hooks/useHandleData";
import { useQueryTable } from "@/hooks/useQueryTable";

import { page } from "./sysAdminLog";

onMounted(() => {
  getTableList();
});

const searchColumns = reactive([
  {
    label: "标题",
    prop: "title"
  },
  {
    label: "账号",
    prop: "account"
  },
  {
    label: "IP",
    prop: "requestIp"
  },
  {
    label: "耗时",
    prop: "times",
    valueType: "number"
  },
  {
    label: "状态",
    prop: "state",
    valueType: "select",
    options: [
      { label: "正常", value: "1" },
      { label: "异常", value: "0" }
    ]
  }
]);

const tableRef = ref();
const tableColumns = [
  {
    prop: "expand",
    type: "expand",
    cellRenderer: ({ row, props }) => (
      <el-descriptions title="">
        <el-descriptions-item label="请求url">
          {row.requestUrl}
        </el-descriptions-item>
        <el-descriptions-item label="方法名">{row.method}</el-descriptions-item>
        <el-descriptions-item label="参数">
          {row.requestBody}
        </el-descriptions-item>
        <el-descriptions-item label="响应">{row.response}</el-descriptions-item>
        <el-descriptions-item label="异常信息">
          {row.message}
        </el-descriptions-item>
      </el-descriptions>
    )
  },
  {
    prop: "title",
    label: "标题"
  },
  {
    prop: "account",
    label: "账号"
  },
  {
    prop: "requestIp",
    label: "IP"
  },
  {
    prop: "times",
    label: "耗时(ms)",
    cellRenderer: ({ row, props }) =>
      row.times > 3000 ? (
        <el-tag type="danger"> {row.times} </el-tag>
      ) : (
        <el-tag> {row.times} </el-tag>
      )
  },
  {
    prop: "requestTime",
    label: "请求时间",
    minWidth: 180
  },
  {
    prop: "state",
    label: "状态",
    cellRenderer: ({ row, props }) =>
      row.state ? (
        <el-tag type="success">正常</el-tag>
      ) : (
        <el-tag type="danger">异常</el-tag>
      )
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

// 新增、查看、编辑
const editDrawerRef = ref();
const openEdit = async (row, isView) => {
  if (row && row.id) {
    editDrawerRef.value.acceptParams(row, getTableList, isView);
  } else {
    editDrawerRef.value.acceptParams({}, getTableList, isView);
  }
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.id}】`);
  getTableList();
};

/** 批量删除 */
const handleDeleteBatch = async () => {
  const ids = selectedData.value.map(item => item.id);
  await useHandleData(dels, ids, `删除${ids.length}条选中数据`);
  selectedData.value = [];
  tableRef.value.getTableRef().clearSelection();
  getTableList();
};

const {
  colProps,
  adaptiveConfig,
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
  handleSelectionChange,
  onSelectionCancel
} = useQueryTable(tableRef, getTableList);
</script>
