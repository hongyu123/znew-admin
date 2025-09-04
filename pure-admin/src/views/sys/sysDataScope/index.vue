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
      :colProps="colProps"
      :prevent="true"
      @change="handleSearchChange"
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="数据权限表"
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
          v-perms="['sysDataScope:del']"
          type="danger"
          plain
          @click="handleDeleteBatch"
        >
          批量删除
        </el-button>
        <el-button
          v-perms="['sysDataScope:add']"
          type="primary"
          :icon="useRenderIcon(EpCirclePlus)"
          @click="openEdit()"
        >
          新增数据权限配置
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
              v-perms="['sysDataScope:edit']"
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
              v-perms="['sysDataScope:del']"
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
import { page, detail, del, dels } from "./sysDataScope";

onMounted(() => {
  getTableList();
});

const searchColumns = reactive([
  {
    label: "配置类型",
    prop: "configType",
    valueType: "radio",
    options: [
      { label: "用户", value: "1" },
      { label: "组织机构", value: "2" }
    ]
  },
  {
    label: "配置名称",
    prop: "params.configName_like"
  },
  {
    label: "数据key",
    prop: "params.dataKey_like"
  }
]);

const inputColumns = ["params.configName_like", "params.dataKey_like"];
/** 搜索表单字段变化处理 */
const handleSearchChange = (values, column) => {
  if (inputColumns.indexOf(column.prop) < 0) {
    getTableList();
  }
};

const tableRef = ref();
const tableColumns = [
  { label: "勾选列", type: "selection" },
  {
    prop: "configType",
    label: "配置类型",
    cellRenderer: ({ row, props }) =>
      row.configType == 1 ? (
        <el-tag type="primary">用户</el-tag>
      ) : (
        <el-tag type="success">组织机构</el-tag>
      )
  },
  {
    prop: "configName",
    label: "配置名称"
  },
  {
    prop: "dataKey",
    label: "数据key"
  },
  {
    prop: "dataScopeDesc",
    label: "数据权限"
  },
  {
    label: "操作",
    //fixed: "right",
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
const openEdit = (row, isView) => {
  if (row && row.id) {
    editDrawerRef.value.acceptParams({ ...row }, getTableList, isView);
  } else {
    editDrawerRef.value.acceptParams(
      { configType: 2, dataScope: "OrganizationAndChildren" },
      getTableList,
      isView
    );
  }
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.configName}】`);
  getTableList();
};

/** 批量删除 */
const handleDeleteBatch = async () => {
  const ids = selectedData.value.map(item => item.id);
  await useHandleData(dels, ids, `删除${ids.length}条选中数据`);
  selectedData.value = [];
  tableRef.value.getTableRef().clearSelection();
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
