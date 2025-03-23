<template>
  <div :class="['flex', 'justify-between', deviceDetection() && 'flex-wrap']">
    <tree
      ref="treeRef"
      :class="['mr-2', deviceDetection() ? 'w-full' : 'min-w-[200px]']"
      :treeData="treeData"
      :treeLoading="treeLoading"
      @tree-select="onTreeSelect"
    />
    <div
      :class="[deviceDetection() ? ['w-full', 'mt-2'] : 'w-[calc(100%-200px)]']"
    >
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
        title="系统用户"
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
            新增系统用户
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
    </div>
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
import { deviceDetection } from "@pureadmin/utils";

import tree from "./tree.vue";
import EditDrawer from "./edit.vue";
import { enums } from "@/api/sys/common";
import { page, detail, del, dels } from "./sysUser";
import { tree as orgTree } from "../sysOrganization/sysOrganization";

//组织机构树
const treeData = ref([]);
const treeLoading = ref(true);
function onTreeSelect({ id, selected }) {
  queryParams.value.orgId = id;
  getTableList();
}

onMounted(() => {
  getTableList();
  enums("EnableState").then(res => {
    searchColumns.find(item => item.prop == "state").options = res.data;
  });
  orgTree("Enable").then(res => {
    treeData.value = res.data;
    treeLoading.value = false;
  });
});

const searchColumns = reactive([
  {
    label: "账号",
    prop: "params.account_like"
  },
  {
    label: "昵称",
    prop: "params.nickname_like"
  },
  {
    label: "手机号码",
    prop: "params.phone_like"
  },
  {
    label: "状态",
    prop: "state",
    valueType: "select",
    options: []
  }
]);

const inputColumns = [
  "params.account_like",
  "params.nickname_like",
  "params.phone_like"
];
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
    prop: "account",
    label: "账号"
  },
  {
    prop: "nickname",
    label: "昵称"
  },
  {
    prop: "phone",
    label: "手机号码",
    minWidth: 120
  },
  {
    prop: "avatar",
    label: "头像",
    cellRenderer: ({ row, props }) => (
      <el-avatar shape="square" fit="contain" src={row.avatar}></el-avatar>
    )
  },
  {
    prop: "state",
    label: "状态",
    cellRenderer: ({ row, props }) =>
      row.state ? (
        <el-tag type="success">启用</el-tag>
      ) : (
        <el-tag type="danger">禁用</el-tag>
      )
  },
  {
    prop: "createTime",
    label: "创建时间",
    minWidth: 180
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
    editDrawerRef.value.acceptParams({ state: "Enable" }, getTableList, isView);
  }
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.account}】`);
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
