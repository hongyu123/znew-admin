<script setup lang="tsx">
import dayjs from "dayjs";
import { reactive, ref, onMounted } from "vue";
import { deviceDetection } from "@pureadmin/utils";
import type { PaginationProps } from "@pureadmin/table";
import { userLoginLog } from "@/views/sys/sysLoginLog/sysLoginLog";

defineOptions({
  name: "SecurityLog"
});

const loading = ref(true);
const dataList = ref([]);
const pagination = reactive<PaginationProps>({
  total: 0,
  pageSize: 10,
  currentPage: 1,
  background: true,
  layout: "prev, pager, next"
});
const columns: TableColumnList = [
  { prop: "ip", label: "登录IP" },
  {
    prop: "location",
    label: "登录地点"
  },
  {
    prop: "browser",
    label: "浏览器"
  },
  {
    prop: "os",
    label: "操作系统"
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
    prop: "logoutTime",
    label: "登出时间",
    minWidth: 180
  }
];

async function onSearch() {
  loading.value = true;
  const data = await userLoginLog({
    pageNumber: pagination.currentPage,
    pageSize: pagination.pageSize
  });
  dataList.value = data.data;
  pagination.total = data.total;
  pagination.pageSize = data.pageSize;
  pagination.currentPage = data.pageNumber;
  loading.value = false;
}

onMounted(() => {
  onSearch();
});
</script>

<template>
  <div
    :class="[
      'min-w-[180px]',
      deviceDetection() ? 'max-w-[100%]' : 'max-w-[70%]'
    ]"
  >
    <h3 class="my-8">安全日志</h3>
    <pure-table
      row-key="id"
      table-layout="auto"
      :loading="loading"
      :data="dataList"
      :columns="columns"
      :pagination="pagination"
      @page-size-change="onSearch"
      @page-current-change="onSearch"
    />
  </div>
</template>
