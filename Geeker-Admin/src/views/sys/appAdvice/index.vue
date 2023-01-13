<template>
  <div class="table-box">
    <ProTable ref="proTable" title="app建议反馈列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader="scope">
        <el-button
          type="danger"
          :icon="Reading"
          plain
          @click="delsModel(scope.selectedListIds)"
          :disabled="!scope.isSelected"
          v-auth="['read']"
        >
          已读
        </el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button
          v-show="scope.row.readFlag == 0"
          type="danger"
          :icon="Reading"
          link
          @click="delModel(scope.row)"
          v-auth="['read']"
          >已读</el-button
        >
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="AppAdvice">
import { inject, ref, watch } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import { Reading } from "@element-plus/icons-vue";
import { useRoute } from "vue-router";
import { OtherStore } from "@/stores/modules/other";
import { page, read, reads } from "@/api/sys/appAdvice";
import { useHandleData } from "@/hooks/useHandleData";
const reload: Function = inject("refresh") as Function;

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();
const route = useRoute();

// 监听路由的变化（防止参数变化,页面不刷新）
watch(
  () => route.fullPath,
  () => {
    reload();
  }
);

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  params.sortByField = params.sortByField ?? "id";
  params.sortByWay = params.sortByWay ?? "desc";
  return page(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  { type: "selection", fixed: "left", width: 50 },
  {
    prop: "userNickname",
    label: "用户昵称"
  },
  {
    prop: "userPhone",
    label: "用户电话"
  },
  {
    prop: "category",
    label: "问题分类",
    search: { el: "select" },
    enum: [
      { label: "产品建议", value: "产品建议" },
      { label: "问题反馈", value: "问题反馈" }
    ]
  },
  // {
  //   prop: "picture",
  //   label: "图片",
  //   render: (scope: { row: any }) => {
  //     if (scope.row.picture) {
  //       return <el-avatar shape="square" fit="contain" src={scope.row.picture}></el-avatar>;
  //     }
  //   }
  // },
  {
    prop: "content",
    label: "建议内容"
  },
  // {
  //   prop: "phone",
  //   label: "联系电话",
  //   width: 120,
  //   search: { el: "input", key: "phone_like" }
  // },
  {
    prop: "createTime",
    label: "反馈时间",
    width: 180
  },
  {
    prop: "readFlag",
    label: "是否已读",
    search: { el: "select", defaultValue: route.query.readFlag ? 0 : -1 },
    enum: [
      { label: "全部", value: -1 },
      { label: "未读", value: 0 },
      { label: "已读", value: 1 }
    ]
  },
  { prop: "operation", label: "操作", fixed: "right", width: 100 }
];

const otherStore = OtherStore();
//删除
const delModel = async (params: any) => {
  await useHandleData(read, { id: params.id }, `已读`);
  proTable.value.getTableList();
  otherStore.loadUnreadCnt();
};

// 批量删除
const delsModel = async (ids: string[]) => {
  await useHandleData(reads, ids, "批量已读");
  proTable.value.clearSelection();
  proTable.value.getTableList();
  otherStore.loadUnreadCnt();
};
</script>
