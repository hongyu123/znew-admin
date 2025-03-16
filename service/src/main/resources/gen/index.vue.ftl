<template>
  <div class="main">
    <PlusSearch
      v-show="showSearch"
      v-model="queryParams"
      class="bg-bg_color px-8 py-[12px]"
      :columns="searchColumns"
      :show-number="5"
      label-width="80"
      label-position="right"
      :colProps="{ xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }"
      @change="handleSearchChange"
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="${tableRemark}"
      :columns="tableColumns"
      @refresh="handleSearch"
      @showSearch="onShowSearch"
    >
      <template #buttons>
        <div v-show="selectedData.length > 0" class="flex-auto">
          <span
            style="font-size: var(--el-font-size-base)"
            class="text-[rgba(42,46,54,0.5)] dark:text-[rgba(220,220,242,0.5)]"
          >
            已选 {{ selectedData.length }} 项
          </span>
          <el-button type="primary" text @click="onSelectionCancel"
            >取消选择</el-button
          >
        </div>
        <el-button
          v-show="selectedData.length > 0"
          type="danger"
          plain
          @click="handleDeleteBatch"
        >
          批量删除
        </el-button>
        <el-button
          type="primary"
          :icon="useRenderIcon(EpCirclePlus)"
          @click="openEdit()"
        >
          新增${tableRemark}
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          ref="tableRef"
          row-key="id"
          align-whole="center"
          table-layout="auto"
          :loading="loading"
          :size="size"
          adaptive
          :adaptiveConfig="{ offsetBottom: 108 }"
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
              class="reset-margin !outline-none"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(EpView)"
              @click="openEdit(row, true)"
            >
              详情
            </el-button>
            <el-button
              class="reset-margin"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(EpEditPen)"
              @click="openEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              class="reset-margin"
              link
              type="danger"
              :size="size"
              :icon="useRenderIcon(EpDelete)"
              @click="handleDelete(row)"
            >
              删除
            </el-button>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
    <EditDrawer ref="editDrawerRef" />
  </div>
</template>

<script setup lang="jsx">
import "plus-pro-components/es/components/search/style/css";

import EpCirclePlus from "@iconify-icons/ep/circle-plus";
import EpDelete from "@iconify-icons/ep/delete";
import EpEditPen from "@iconify-icons/ep/edit-pen";
import EpView from "@iconify-icons/ep/view";

import { ref, reactive, onMounted } from "vue";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { useHandleData } from "@/hooks/useHandleData";
import { useQueryTable } from "@/hooks/useQueryTable";

import EditDrawer from "./edit.vue";
import { enums } from "@/api/sys/common";
import { page, detail, del, dels } from "./${beanName}";

onMounted(() => {
  getTableList();
<#list columnList as c>
  <#if c.formType=='enum' && c.searchFlag==1>
  enums("${c.columnRemark}").then(res => {
    searchColumns.find(item => item.prop == "${c.property}").options = res.data;
  });
  </#if>
</#list>
});

const searchColumns = reactive([
<#list columnList as c>
<#if c.searchFlag==1>
  {
    label: "${c.label}",
  <#if c.formType=='input' || c.formType=='phone'>
    prop: "params.${c.property}_like",
  <#else>
    prop: "${c.property}",
  </#if>
  <#if c.formType=='date' || c.formType=='datetime'>
    valueType: "date-picker",
    fieldProps: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
  <#elseif c.formType=='select' || c.formType=='radio'>
    valueType: "select",
    options: [
      { label: "启用", value: "1" },
      { label: "禁用", value: "0" }
    ]
  <#elseif c.formType=='enum'>
    valueType: "select",
    options: []
  </#if>
  },
</#if>
</#list>
]);

const inputColumns = [<#list columnList as c><#if c.searchFlag==1 && (c.formType=='input' || c.formType=='phone')>"params.${c.property}_like", </#if></#list>];
/** 搜索表单字段变化处理 */
const handleSearchChange = (values, column) => {
  if (inputColumns.indexOf(column.prop) < 0) {
    getTableList();
  }
};

const tableRef = ref();
const tableColumns = [
  { label: "勾选列", type: "selection" },
<#list columnList as c>
<#if c.listFlag==1>
  {
  <#if c.formType=='enum'>
    prop: "${c.property}Desc",
  <#else>
    prop: "${c.property}",
  </#if>
    label: "${c.label}",
  <#if c.formType=='phone'>
    minWidth: 120
  <#elseif c.formType=='date'>
    minWidth: 110,
  <#elseif c.formType=='datetime'>
    minWidth: 180
  <#elseif c.formType=='select' || c.formType=='radio'>
    cellRenderer: ({ row, props }) =>
      row.state ? (
        <el-tag type="success">启用</el-tag>
      ) : (
        <el-tag type="danger">禁用</el-tag>
      )
  <#elseif c.formType=='picture'>
    cellRenderer: ({ row, props }) => (
      <el-avatar shape="square" fit="contain" src={row.${c.property}}></el-avatar>
    )
  </#if>
  },
</#if>
</#list>
  {
    label: "操作",
    fixed: "right",
    slot: "operation",
    width: 210 //3个图标+按钮210
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

// 新增、查看、编辑
const editDrawerRef = ref();
const openEdit = async (row, isView) => {
  if (row && row.id) {
    editDrawerRef.value.acceptParams(row, getTableList, isView);
  } else {
    editDrawerRef.value.acceptParams({}, getTableList, isView);
  }
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `${r'删除【${row.id}】'}`);
  getTableList();
};

/** 批量删除 */
const handleDeleteBatch = async () => {
  const ids = selectedData.value.map(item => item.id);
  await useHandleData(dels, ids, `${r'删除${ids.length}条选中数据'}`);
  selectedData.value = [];
  tableRef.value.getTableRef().clearSelection();
  getTableList();
};

const {
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
