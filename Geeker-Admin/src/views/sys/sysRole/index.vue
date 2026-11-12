<template>
  <div class="table-box">
    <ProTable ref="proTable" title="系统角色列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" icon="CirclePlus" @click="openEditForm()">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" link @click="toUserRole(scope.row)">授权用户</el-button>
        <el-button type="primary" icon="EditPen" link @click="openEditForm(scope.row)">编辑</el-button>
        <el-button type="danger" icon="Delete" link @click="delModel(scope.row)">删除</el-button>
      </template>
    </ProTable>

    <SysRoleDrawer ref="editFormRef" />
  </div>
</template>

<script setup lang="jsx" name="useProTable">
import { ref } from "vue";
import { useRouter } from "vue-router";
import ProTable from "@/components/ProTable/index.vue";
import SysRoleDrawer from "./edit.vue";

import { useHandleData } from "@/hooks/useHandleData";
import { enums } from "@/api/modules/common";
import { page, detail } from "./api.js";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = params => {
  return page(params);
};

// 表格配置项
const columns = [
  {
    prop: "name",
    label: "角色",
    search: { el: "input", key: "name" }
  },
  {
    prop: "code",
    label: "编码",
    search: { el: "input", key: "code" }
  },
  {
    prop: "sort",
    label: "排序",
    sortable: true
  },
  {
    prop: "state",
    label: "状态",
    enum: () => enums("EnableState"),
    search: { el: "select" }
  },
  {
    prop: "createTime",
    label: "创建时间",
    width: 180
  },
  { prop: "operation", label: "操作", fixed: "right", width: 220 }
];

// 新增、查看、编辑
const editFormRef = ref();
const openEditForm = async (row, isView = false) => {
  if (row && row.id) {
    const { data } = await detail(row.id);
    editFormRef.value.acceptParams(data, getTableList, isView);
  } else {
    editFormRef.value.acceptParams({ state: "Enable" }, getTableList, isView);
  }
};

//删除
const delModel = async params => {
  await useHandleData(del, params.id, `删除【${params.name}】`);
  proTable.value.getTableList();
};

//跳转授权用户
const router = useRouter();
const toUserRole = params => {
  router.push(`/sys/sysUserRole/${params.id}`);
};
</script>
