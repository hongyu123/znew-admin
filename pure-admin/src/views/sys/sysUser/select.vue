<template>
  <div class="main">
    <PlusSearch
      v-show="showSearch"
      v-model="queryParams"
      class="bg-bg_color px-8 py-[12px]"
      :columns="searchColumns"
      :show-number="5"
      :hasLabel="false"
      :prevent="true"
      :hasReset="false"
      @search="handleSearch"
      @reset="handleReset"
    />
    <pure-table
      ref="tableRef"
      row-key="id"
      align-whole="center"
      table-layout="auto"
      :loading="loading"
      :data="dataList"
      :columns="tableColumns"
      :pagination="{
        background: true,
        total,
        currentPage: queryParams.pageNumber,
        pageSize: queryParams.pageSize
      }"
      :header-cell-style="{
        background: 'var(--el-fill-color-light)',
        color: 'var(--el-text-color-primary)'
      }"
      @selection-change="handleSelectionChange"
      @page-size-change="handleSizeChange"
      @page-current-change="handleCurrentChange"
    />
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

import { page } from "./sysUser";

const props = defineProps(["hasNoRoleId"]);

onMounted(() => {
  getTableList();
});

const searchColumns = reactive([
  {
    label: "账号",
    prop: "params.account_like"
  },
  {
    label: "昵称",
    prop: "params.nickname_like"
  },
  {
    label: "手机号码",
    prop: "params.phone_like"
  }
]);

// const inputColumns = [
//   "params.account_like",
//   "params.nickname_like",
//   "params.phone_like"
// ];
// /** 搜索表单字段变化处理 */
// const handleSearchChange = (values, column) => {
//   if (inputColumns.indexOf(column.prop) < 0) {
//     getTableList();
//   }
// };

const tableRef = ref();
const tableColumns = [
  { label: "勾选列", type: "selection" },
  {
    prop: "account",
    label: "账号"
  },
  {
    prop: "nickname",
    label: "昵称"
  },
  {
    prop: "phone",
    label: "手机号码",
    minWidth: 120
  },
  {
    prop: "avatar",
    label: "头像",
    cellRenderer: ({ row, props }) => (
      <el-avatar shape="square" fit="contain" src={row.avatar}></el-avatar>
    )
  }
];

/** 表格获取数据 */
const getTableList = async () => {
  if (props.hasNoRoleId > 0) {
    queryParams.value.params.hasNoRoleId = props.hasNoRoleId;
  }
  loading.value = true;
  const res = await page(queryParams.value);
  total.value = res.total;
  dataList.value = res.data;
  loading.value = false;
};

const getSelected = () => {
  return selectedData.value;
};

defineExpose({ getSelected });
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
