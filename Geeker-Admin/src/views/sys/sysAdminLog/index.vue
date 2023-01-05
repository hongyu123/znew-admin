<template>
  <div class="table-box">
    <ProTable ref="proTable" title="admin日志列表" :columns="columns" :requestApi="getTableList" :toolButton="false">
      <template #expand="scope">
        <el-descriptions title="">
          <el-descriptions-item label="请求url">{{ scope.row.requestUrl }}</el-descriptions-item>
          <el-descriptions-item label="方法名">{{ scope.row.method }}</el-descriptions-item>
          <el-descriptions-item label="参数">{{ scope.row.requestBody }}</el-descriptions-item>
          <el-descriptions-item label="响应">{{ scope.row.response }}</el-descriptions-item>
          <el-descriptions-item label="异常信息">{{ scope.row.message }}</el-descriptions-item>
        </el-descriptions>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="SysAdminLog">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import { page } from "@/api/sys/sysAdminLog";

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
    prop: "title",
    label: "标题",
    search: { el: "input", key: "title_like" }
  },
  {
    prop: "account",
    label: "账号",
    search: { el: "input", key: "account_like" }
  },
  {
    prop: "requestIp",
    label: "IP",
    search: { el: "input", key: "requestIp_like" }
  },
  {
    prop: "times",
    label: "耗时(ms)",
    search: { el: "input", key: "times_gt" },
    render: (scope: { row: any }) => {
      return scope.row.times > 3000 ? <el-tag type="danger"> {scope.row.times} </el-tag> : <el-tag> {scope.row.times} </el-tag>;
    }
  },
  {
    prop: "requestTime",
    label: "请求时间",
    width: 180
  },
  // {
  //   prop: "requestUrl",
  //   label: "请求url"
  // },
  // {
  //   prop: "method",
  //   label: "方法名"
  // },
  // {
  //   prop: "requestBody",
  //   label: "参数"
  // },
  // {
  //   prop: "response",
  //   label: "响应"
  // },
  {
    prop: "state",
    label: "状态",
    search: { el: "select" },
    enum: [
      { label: "正常", value: 1 },
      { label: "异常", value: 0 }
    ],
    render: (scope: { row: any }) => {
      return scope.row.state == 1 ? <el-tag> 正常 </el-tag> : <el-tag type="danger"> 异常 </el-tag>;
    }
  }
  // {
  //   prop: "message",
  //   label: "异常信息"
  // }
];
</script>
