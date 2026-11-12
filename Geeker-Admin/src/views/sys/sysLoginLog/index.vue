<template>
  <div class="table-box">
    <ProTable ref="proTable" :columns="columns" :request-api="getTableList" row-key="id">
      <!-- 展开行 -->
      <template #expand="scope">
        <el-descriptions title="">
          <el-descriptions-item label="登出时间">{{ scope.row.logoutTime }}</el-descriptions-item>
          <el-descriptions-item label="浏览器">{{ scope.row.userAgent }}</el-descriptions-item>
        </el-descriptions>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button v-if="scope.row.onlineFlag === 1" type="danger" link :icon="SwitchButton" @click="handleKickout(scope.row)">
          强制下线
        </el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup name="loginLog">
import { reactive, ref } from "vue";
import { SwitchButton } from "@element-plus/icons-vue";

import ProTable from "@/components/ProTable/index.vue";
import { useHandleData } from "@/hooks/useHandleData";

import { enums } from "@/api/modules/common";
import { page, kickoutLoginLog } from "./api";

// ProTable 实例
const proTable = ref();

// 登录状态字典(1成功 0失败)
const stateEnum = [
  { label: "成功", value: 1, tagType: "success" },
  { label: "失败", value: 0, tagType: "danger" }
];

// 在线状态字典
const onlineFlagEnum = [
  { label: "在线", value: 1, tagType: "success" },
  { label: "离线", value: 0, tagType: "info" }
];

// 表格配置项
const columns = reactive([
  { type: "expand", width: 60 },
  // { prop: "id", label: "日志编号", width: 90 },
  // { prop: "userId", label: "用户ID", width: 90 },
  { prop: "account", label: "用户账号", search: { el: "input" } },
  { prop: "ip", label: "登录IP", search: { el: "input" } },
  { prop: "location", label: "登录地点", search: { el: "input" } },
  // { prop: "userAgent", label: "浏览器", width: 180 },
  { prop: "state", label: "登录状态", width: 100, tag: true, enum: stateEnum, search: { el: "select" } },
  { prop: "message", label: "提示消息", width: 160 },
  {
    prop: "loginTime",
    label: "登录时间",
    // sortable: true,
    search: { el: "date-picker", key: "loginTime_gt", props: { type: "datetime", valueFormat: "YYYY-MM-DD HH:mm:ss" } },
    width: 180
  },
  {
    prop: "loginTime",
    label: "至",
    search: { el: "date-picker", key: "loginTime_lt", props: { type: "datetime", valueFormat: "YYYY-MM-DD HH:mm:ss" } },
    isShow: false
  },

  { prop: "onlineFlag", label: "在线状态", width: 100, tag: true, enum: onlineFlagEnum, search: { el: "select" } },
  { prop: "logoutType", label: "登出类型", width: 100, tag: true, enum: () => enums("LogoutType") },
  { prop: "logoutTime", label: "登出时间", width: 180 },
  // { prop: "expireTime", label: "失效时间", width: 180 },
  { prop: "operation", label: "操作", fixed: "right", width: 110 }
]);

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = params => {
  // params.sortByField = params.sortByField ?? "id";
  // params.sortByWay = params.sortByWay ?? "DESC";
  return page(params);
};

// 强制下线
const handleKickout = async row => {
  await useHandleData(kickoutLoginLog, row.id, `强制【${row.account}】下线`);
  proTable.value?.getTableList();
};
</script>
