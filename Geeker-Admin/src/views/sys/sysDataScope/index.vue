<template>
  <div class="table-box">
    <ProTable ref="proTable" title="数据权限配置列表" :columns="columns" :request-api="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" icon="CirclePlus" @click="openEditForm()">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="EditPen" link @click="openEditForm(scope.row)">编辑</el-button>
        <el-button type="danger" icon="Delete" link @click="delModel(scope.row)">删除</el-button>
      </template>
    </ProTable>

    <SysDataScopeDrawer ref="editFormRef" />
  </div>
</template>

<script setup lang="jsx" name="SysDataScope">
import { ref } from "vue";
import ProTable from "@/components/ProTable/index.vue";
import SysDataScopeDrawer from "./edit.vue";

import { useHandleData } from "@/hooks/useHandleData";
import { enums } from "@/api/modules/common";
import { DataScopeConfigTypeEnum } from "@/enums/commonEnum";
import { page, detail, del } from "./api";

// 获取 ProTable 元素，调用其获取刷新数据方法
const proTable = ref();

// 请求列表（去掉空查询参数，避免后端类型转换问题）
const getTableList = params => {
  return page(params);
};

// 表格配置项（展示所有字段，customIds 除外）
const columns = [
  { prop: "id", label: "ID", width: 80 },
  {
    prop: "configType",
    label: "配置类型",
    enum: DataScopeConfigTypeEnum,
    search: { el: "select" }
  },
  {
    prop: "configName",
    label: "配置名称",
    search: { el: "input" }
  },
  {
    prop: "dataKey",
    label: "数据key",
    search: { el: "input" }
  },
  {
    prop: "dataScopeDesc",
    label: "数据权限"
  },
  { prop: "operation", label: "操作", fixed: "right", width: 180 }
];

// 新增、编辑
const editFormRef = ref();
const openEditForm = async row => {
  if (row && row.id) {
    const { data } = await detail(row.id);
    editFormRef.value.acceptParams(data, proTable.value.getTableList);
  } else {
    editFormRef.value.acceptParams({ configType: 1, dataScope: "Organization", customIds: [] }, proTable.value.getTableList);
  }
};

// 删除
const delModel = async row => {
  await useHandleData(del, row.id, `删除【${row.configName}】`);
  proTable.value.getTableList();
};
</script>
