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
      @search="handleSearch"
      @reset="handleReset"
      @keydown.enter="handleSearch"
    />

    <PureTableBar
      title="系统角色"
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
          v-perms="['sysRole:del']"
          type="danger"
          plain
          @click="handleDeleteBatch"
        >
          批量删除
        </el-button>
        <el-button
          v-perms="['sysRole:add']"
          type="primary"
          :icon="useRenderIcon(EpCirclePlus)"
          @click="openEdit()"
        >
          新增系统角色
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
              v-perms="['sysRole:role']"
              type="primary"
              link
              @click="toUserRole(row)"
            >
              授权用户
            </el-button>
            <el-button
              v-perms="['sysRole:edit']"
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
              v-perms="['sysRole:del']"
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
import { useRouter } from "vue-router";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { useHandleData } from "@/hooks/useHandleData";
import { useQueryTable } from "@/hooks/useQueryTable";
import { useMultiTagsStoreHook } from "@/store/modules/multiTags";

import EditDrawer from "./edit.vue";
import { enums } from "@/api/sys/common";
import { page, detail, del, dels } from "./sysRole";

onMounted(() => {
  getTableList();
});

const searchColumns = reactive([
  {
    label: "角色名",
    prop: "name"
  },
  {
    label: "角色编码",
    prop: "code"
  }
]);

const tableRef = ref();
const tableColumns = [
  { label: "勾选列", type: "selection" },
  {
    prop: "name",
    label: "角色名"
  },
  {
    prop: "code",
    label: "角色编码"
  },
  {
    prop: "sort",
    label: "排序",
    sortable: "sort"
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
    prop: "remark",
    label: "备注"
  },
  {
    prop: "createTime",
    label: "创建时间",
    minWidth: 180
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
const openEdit = async (row, isView) => {
  if (row && row.id) {
    const res = await detail(row.id);
    editDrawerRef.value.acceptParams(res.data, getTableList, isView);
  } else {
    editDrawerRef.value.acceptParams(
      { state: "Enable", sort: 0 },
      getTableList,
      isView
    );
  }
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.id}】`);
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

//跳转授权用户
const router = useRouter();
const toUserRole = row => {
  const parameter = { roleId: row.id };
  useMultiTagsStoreHook().handleTags("push", {
    path: "/sysRole/users",
    name: "sysUserRole",
    param: parameter,
    meta: {
      title: `[${row.name}]角色授权用户`
    }
  });
  router.push({ name: "sysUserRole", params: parameter });
  //router.push(`${params.id}`);
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
