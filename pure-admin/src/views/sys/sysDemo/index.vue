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
      title="系统示例"
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
          v-perms="['sysDemo:del']"
          type="danger"
          plain
          @click="handleDeleteBatch"
        >
          批量删除
        </el-button>
        <el-button
          v-perms="['sysDemo:add']"
          type="primary"
          :icon="useRenderIcon(EpCirclePlus)"
          @click="openEdit()"
        >
          新增系统测试
        </el-button>
        <el-button
          v-perms="['sysDemo:import']"
          type="primary"
          :icon="Upload"
          plain
          @click="importFile"
          >导入</el-button
        >
        <el-button
          v-perms="['sysDemo:export']"
          type="warning"
          :icon="Download"
          plain
          @click="exportFile"
          >导出</el-button
        >
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
              v-perms="['sysDemo:view']"
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
              v-perms="['sysDemo:edit']"
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
              v-perms="['sysDemo:del']"
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
    <ImportExcel ref="importRef" />
  </div>
</template>

<script setup lang="jsx">
import "plus-pro-components/es/components/search/style/css";

import EpCirclePlus from "@iconify-icons/ep/circle-plus";
import EpDelete from "@iconify-icons/ep/delete";
import EpEditPen from "@iconify-icons/ep/edit-pen";
import EpView from "@iconify-icons/ep/view";
import { Upload, Download } from "@element-plus/icons-vue";

import { ref, reactive, onMounted } from "vue";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";

import EditDrawer from "./edit.vue";
import ImportExcel from "@/components/ImportExcel/index.vue";
import { enums } from "@/api/sys/common";
import { page, detail, del, dels, imp, exp } from "./sysDemo";

import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { useHandleData } from "@/hooks/useHandleData";
import { useQueryTable } from "@/hooks/useQueryTable";
import { downloadByData } from "@pureadmin/utils";

onMounted(() => {
  getTableList();
  enums("Gender").then(res => {
    searchColumns.find(item => item.prop == "gender").options = res.data;
  });
});

const searchColumns = reactive([
  {
    label: "名称",
    prop: "name"
  },
  {
    label: "手机",
    prop: "params.phone_like"
  },
  {
    label: "性别",
    prop: "gender",
    valueType: "select",
    options: []
  },
  {
    label: "启用状态",
    prop: "state",
    valueType: "select",
    options: [
      { label: "启用", value: "1" },
      { label: "禁用", value: "0" }
    ]
  },
  {
    label: "生日",
    prop: "birth",
    valueType: "date-picker",
    fieldProps: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
  },
  {
    label: "注册时间起",
    prop: "params.registTime_gt",
    valueType: "date-picker",
    fieldProps: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
  },
  {
    label: "注册时间止",
    prop: "params.registTime_lt",
    valueType: "date-picker",
    fieldProps: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
  }
]);

const inputColumns = ["name", "params.phone_like"];
/** 搜索表单字段变化处理 */
const handleSearchChange = (values, column) => {
  if (inputColumns.indexOf(column.prop) < 0) {
    getTableList();
  }
};

const tableRef = ref();
const tableColumns = [
  { label: "勾选列", type: "selection" },
  { prop: "name", label: "名称" },
  { prop: "genderDesc", label: "性别" },
  {
    prop: "state",
    label: "启用状态",
    cellRenderer: ({ row, props }) =>
      row.state ? (
        <el-tag type="success">启用</el-tag>
      ) : (
        <el-tag type="danger">禁用</el-tag>
      )
  },
  {
    prop: "birth",
    label: "生日",
    minWidth: 110,
    sortable: "birth"
  },
  {
    prop: "registTime",
    label: "注册时间",
    minWidth: 180
  },
  {
    prop: "avatarUrl",
    label: "头像",
    cellRenderer: ({ row, props }) => (
      <el-avatar shape="square" fit="contain" src={row.avatarUrl}></el-avatar>
    )
  },
  {
    prop: "phone",
    label: "手机",
    minWidth: 120
  },
  {
    prop: "introduction",
    label: "简介",
    showOverflowTooltip: true,
    width: 120
  },
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
    const res = await detail(row.id);
    editDrawerRef.value.acceptParams(res.data, getTableList, isView);
  } else {
    editDrawerRef.value.acceptParams({ ...row }, getTableList, isView);
  }
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.name}】`);
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

// 导出
const exportFile = () => {
  exp(queryParams.value).then(response => {
    if (response.headers && response.headers["content-disposition"]) {
      const fileName = decodeURI(
        response.headers["content-disposition"].replace(
          "attachment;filename=",
          ""
        )
      );
      downloadByData(response.data, fileName);
    } else {
      downloadByData(response, "系统示例.xlsx");
    }
  });
};
// 导入
const importRef = ref();
const importFile = () => {
  let params = {
    title: "导入示例",
    template: "SysDemo",
    tempApi: exp,
    importApi: imp,
    getTableList: getTableList
  };
  importRef.value.acceptParams(params);
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
