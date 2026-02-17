<template>
  <div class="main">
    <PlusSearch
      v-show="showSearch"
      v-model="queryParams"
      class="bg-bg_color px-8 py-[12px]"
      :columns="searchColumns"
      :show-number="5"
      label-width="90"
      label-position="right"
      :colProps="colProps"
      :prevent="true"
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="系统登录日志"
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
        >
          <template #operation="{ row }">
            <el-button
              v-if="row.onlineFlag == 1"
              v-perms="['sysLoginLog:kickout']"
              link
              type="danger"
              :size="size"
              @click="handleDelete(row)"
            >
              强退
            </el-button>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
  </div>
</template>

<script setup lang="jsx">
import "plus-pro-components/es/components/search/style/css";

import { ref, reactive, onMounted } from "vue";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";
import { useQueryTable } from "@/hooks/useQueryTable";
import { useHandleData } from "@/hooks/useHandleData";

import { enums } from "@/api/sys/common";
import { page, kickout } from "./sysLoginLog.js";

onMounted(() => {
  getTableList();
  enums("LogoutType").then(res => {
    searchColumns.find(item => item.prop == "logoutType").options = res.data;
  });
});

const searchColumns = reactive([
  {
    label: "用户账号",
    prop: "account"
  },
  {
    label: "登录IP",
    prop: "ip"
  },
  {
    label: "登录地点",
    prop: "location"
  },
  {
    label: "状态",
    prop: "state",
    valueType: "select",
    options: [
      { label: "成功", value: 1 },
      { label: "失败", value: 0 }
    ]
  },
  {
    label: "在线状态",
    prop: "onlineFlag",
    valueType: "select",
    options: [
      { label: "在线", value: 1 },
      { label: "下线", value: 0 }
    ]
  },
  {
    label: "登录时间起",
    prop: "params.loginTime_gt",
    valueType: "date-picker",
    fieldProps: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
  },
  {
    label: "登录时间止",
    prop: "params.loginTime_lt",
    valueType: "date-picker",
    fieldProps: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
  },
  {
    label: "登出类型",
    prop: "logoutType",
    valueType: "select",
    options: []
  }
]);

const tableRef = ref();
const tableColumns = [
  {
    prop: "expand",
    type: "expand",
    cellRenderer: ({ row, props }) => (
      <el-descriptions title="">
        <el-descriptions-item label="登出时间">
          {row.logoutTime}
        </el-descriptions-item>
        <el-descriptions-item label="浏览器类型">
          {row.browser}
        </el-descriptions-item>
        <el-descriptions-item label="操作系统">{row.os}</el-descriptions-item>
      </el-descriptions>
    )
  },
  {
    prop: "account",
    label: "用户账号"
  },
  {
    prop: "ip",
    label: "登录IP"
  },
  {
    prop: "location",
    label: "登录地点"
  },
  {
    prop: "state",
    label: "登录状态",
    cellRenderer: ({ row, props }) =>
      row.state ? (
        <el-tag type="success">成功</el-tag>
      ) : (
        <el-tag type="danger">失败</el-tag>
      )
  },
  {
    prop: "message",
    label: "提示消息"
  },
  {
    prop: "onlineFlag",
    label: "在线状态",
    cellRenderer: ({ row, props }) =>
      row.onlineFlag ? (
        <el-tag type="success">在线</el-tag>
      ) : (
        <el-tag type="danger">下线</el-tag>
      )
  },
  {
    prop: "loginTime",
    label: "登录时间",
    minWidth: 180
  },
  {
    prop: "logoutTypeDesc",
    label: "登出类型"
  },
  {
    label: "操作",
    fixed: "right",
    slot: "operation",
    width: 80
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

/** 删除 */
const handleDelete = async row => {
  await useHandleData(kickout, { id: row.id }, `强退【${row.account}】`);
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
