<template>
  <div class="table-box">
    <ProTable ref="proTable" title="${tableRemark}列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader="scope">
        <el-button type="primary" icon="CirclePlus" @click="openEditForm()">新增</el-button>
        <el-button
          type="danger"
          icon="Delete"
          plain
          @click="delsModel(scope.selectedListIds)"
          :disabled="!scope.isSelected"
        >
          删除
        </el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="EditPen" link @click="openEditForm(scope.row)">编辑</el-button>
        <el-button type="danger" icon="Delete" link @click="delModel(scope.row)">删除</el-button>
      </template>
    </ProTable>

    <${className}Drawer ref="editFormRef" />
  </div>
</template>

<script setup lang="jsx" name="${className}">
import { ref } from "vue";
import ProTable from "@/components/ProTable/index.vue";
import ${className}Drawer from "./edit.vue";

import { useHandleData } from "@/hooks/useHandleData";
import { page, detail, del, dels } from "./${beanName}";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params) => {
  params.sortByField = params.sortByField ?? "id";
  params.sortByWay = params.sortByWay ?? "DESC";
  return page(params);
};

// 表格配置项
const columns = [
  { type: "selection", fixed: "left", width: 50 },
  // {
  //   prop: "id",
  //   label: "ID"
  // },
  <#list columnList as c>
  <#if c.listFlag==1>
  {
    prop: "${c.property}",
    label: "${c.label}"
    <#if c.formType=='phone'>
    ,width: 120
    <#elseif c.formType=='date'>
    ,width: 110
    <#elseif c.formType=='datetime'>
    ,width: 180
    <#elseif c.formType=='select' || c.formType=='radio'>
    ,width: 110
    <#elseif c.formType=='picture'>
    ,render: (scope) => {
      return <el-avatar shape="square" fit="contain" src={scope.row.${c.property}}></el-avatar>;
    }
    </#if>
    <#if c.searchFlag==1>
      <#if  c.formType=='date' || c.formType=='datetime'>
    ,search: { el: "date-picker", key: "${c.property}", props: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" } }
      <#elseif c.formType=='select' || c.formType=='radio'>
    ,search: { el: "select", key: "${c.property}" }
    ,enum: [
      { label: "是", value: 1 },
      { label: "否", value: 0 }
    ]
      <#else>
    ,search: { el: "input", key: "${c.property}" }
      </#if>
    </#if>
  },
  </#if>
  </#list>
  { prop: "operation", label: "操作", fixed: "right", width: 200 }
];

// 新增、查看、编辑
const editFormRef = ref();
const openEditForm = async (row, isView = false) => {
  if (row && row.id) {
    const { data } = await detail(row.id);
    editFormRef.value.acceptParams(data, proTable.value.getTableList, isView);
  } else {
    editFormRef.value.acceptParams({ state: "Enable" }, proTable.value.getTableList, isView);
  }
};

//删除
const delModel = async (params) => {
  await useHandleData(del, params.id, `删除【${r'${params.name}'}】`);
  proTable.value.getTableList();
};

// 批量删除
const delsModel = async (ids) => {
  await useHandleData(dels, ids, `${r'删除${ids.length}条选中数据'}`);
  proTable.value.clearSelection();
  proTable.value.getTableList();
};
</script>
