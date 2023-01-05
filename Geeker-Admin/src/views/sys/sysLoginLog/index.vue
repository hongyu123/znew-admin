<template>
  <div class="table-box">
    <ProTable ref="proTable" title="系统登录日志列表" :columns="columns" :requestApi="getTableList" :tool-button="false">
      <template #expand="scope">
        <el-descriptions title="">
          <el-descriptions-item label="登出时间">{{ scope.row.logoutTime }}</el-descriptions-item>
          <el-descriptions-item label="浏览器类型">{{ scope.row.browser }}</el-descriptions-item>
          <el-descriptions-item label="操作系统">{{ scope.row.os }}</el-descriptions-item>
        </el-descriptions>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="SysLoginLog">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import { page } from "@/api/sys/sysLoginLog";
import { LogoutType } from "@/api/modules/enum";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  params.sortByField = params.sortByField ?? "id";
  params.sortByWay = params.sortByWay ?? "desc";
  return page(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  {
    prop: "expand",
    type: "expand"
  },
  {
    prop: "account",
    label: "账号",
    search: { el: "input", key: "account_like" }
  },
  {
    prop: "ip",
    label: "登录IP",
    search: { el: "input", key: "ip_like" }
  },
  {
    prop: "location",
    label: "登录地点",
    search: { el: "input", key: "location_like" }
  },
  {
    prop: "state",
    label: "登录状态",
    search: { el: "select" },
    enum: [
      { label: "成功", value: 1 },
      { label: "失败", value: 0 }
    ]
  },
  {
    prop: "message",
    label: "提示消息"
  },
  {
    prop: "onlineFlag",
    label: "在线状态",
    // search: { el: "select" },如果要用在线状态搜索就只能用定时器改变在线状态了
    enum: [
      { label: "在线", value: 1 },
      { label: "下线", value: 0 }
    ]
  },
  {
    prop: "loginTime",
    label: "登录时间",
    search: { el: "date-picker", key: "loginTime_gt", props: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" } },
    width: 180
  },
  {
    prop: "loginTime",
    label: "至",
    search: { el: "date-picker", key: "loginTime_lt", props: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" } },
    isShow: false
  },
  {
    prop: "logoutType",
    label: "登出类型",
    search: { el: "select" },
    enum: LogoutType,
    fieldNames: { label: "desc", value: "value" }
  }
  // {
  //   prop: "logoutTime",
  //   label: "登出时间",
  //   width: 180
  // },

  // {
  //   prop: "browser",
  //   label: "浏览器类型"
  // },
  // {
  //   prop: "os",
  //   label: "操作系统"
  // }
];
</script>
