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
      title="字典表"
      :columns="tableColumns"
      @refresh="handleSearch"
      @showSearch="onShowSearch"
    >
      <template #buttons>
        <el-button
          v-perms="['sysDict:add']"
          type="primary"
          :icon="useRenderIcon(EpCirclePlus)"
          @click="openEdit()"
        >
          新增数据字典
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          ref="tableRef"
          row-key="id"
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
          lazy
          :load="loadChildren"
          :tree-props="{
            hasChildren: 'hasChildren',
            children: 'children',
            checkStrictly: false
          }"
          @sort-change="handleSortChange"
          @selection-change="handleSelectionChange"
          @page-size-change="handleSizeChange"
          @page-current-change="handleCurrentChange"
        >
          <template #operation="{ row }">
            <el-button
              v-perms="['sysDict:add']"
              type="primary"
              :icon="useRenderIcon(EpCirclePlus)"
              link
              @click="openAddChild(row)"
              >新增</el-button
            >
            <el-button
              v-perms="['sysDict:edit']"
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
              v-show="row.childrenNum <= 0"
              v-perms="['sysDict:del']"
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

    <el-dialog
      v-model="itemsDialogShow"
      :title="itemsDialogTitle"
      width="50%"
      center
      draggable
    >
      <div class="mb-2">
        <el-button
          v-perms="['sysDict:add']"
          type="primary"
          @click="openItemsEdit()"
          >新增</el-button
        >
        <el-button
          v-show="itemsSelectedData.length > 0"
          v-perms="['sysDict:del']"
          type="danger"
          plain
          @click="itemsDeleteBatch"
        >
          删除
        </el-button>
      </div>
      <pure-table
        row-key="id"
        align-whole="center"
        table-layout="auto"
        :loading="itemsLoading"
        :data="itemsDataList"
        :columns="itemsColumns"
        :pagination="{
          background: true,
          itemsTotal,
          currentPage: itemsParams.pageNumber,
          pageSize: itemsParams.pageSize
        }"
        :header-cell-style="{
          background: 'var(--el-fill-color-light)',
          color: 'var(--el-text-color-primary)'
        }"
        @selection-change="itemsSelectionChange"
        @page-size-change="itemsSizeChange"
        @page-current-change="itemsCurrentChange"
      >
        <template #operation="{ row }">
          <el-button
            v-perms="['sysDict:edit']"
            class="reset-margin"
            link
            type="primary"
            :icon="useRenderIcon(EpEditPen)"
            @click="openItemsEdit(row)"
          >
            编辑
          </el-button>
          <el-button
            v-perms="['sysDict:del']"
            class="reset-margin"
            link
            type="danger"
            :icon="useRenderIcon(EpDelete)"
            @click="itemsDelete(row)"
          >
            删除
          </el-button>
        </template>
      </pure-table>
    </el-dialog>
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
import { page, children, del, dels } from "./sysDictionary";

onMounted(() => {
  getTableList();
  enums("EnableState").then(res => {
    searchColumns.find(item => item.prop == "state").options = res.data;
  });
});

const searchColumns = reactive([
  {
    label: "状态",
    prop: "state",
    valueType: "select",
    options: []
  },
  {
    label: "字典类型",
    prop: "params.dictKey_like"
  },
  {
    label: "字典名称",
    prop: "params.dictValue_like"
  }
]);

const inputColumns = ["params.dictKey_like", "params.dictValue_like"];
/** 搜索表单字段变化处理 */
const handleSearchChange = (values, column) => {
  if (inputColumns.indexOf(column.prop) < 0) {
    getTableList();
  }
};

const tableRef = ref();
const tableColumns = [
  //{ label: "勾选列", type: "selection" },
  {
    prop: "dictKey",
    label: "字典类型",
    cellRenderer: ({ row, props }) => (
      <el-link
        type="primary"
        onClick={() => {
          itemsDialogOpen(row);
        }}
      >
        {row.dictKey}
      </el-link>
    )
  },
  {
    prop: "dictValue",
    label: "字典名称"
  },
  {
    prop: "stateDesc",
    label: "状态"
  },
  {
    prop: "sort",
    label: "排序值"
  },
  {
    prop: "remark",
    label: "备注"
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
  queryParams.value.pid = 0;
  queryParams.value.leafFlag = 0;
  loading.value = true;
  const res = await page(queryParams.value);
  total.value = res.total;
  dataList.value = res.data;
  loading.value = false;
};

const refreshChildren = pid => {
  children(pid).then(res => {
    tableRef.value.getTableRef().updateKeyChildren(pid, res.data);
  });
};
// 新增、查看、编辑
const editDrawerRef = ref();
const openEdit = (row, isView) => {
  if (row && row.id) {
    editDrawerRef.value.acceptParams(
      { ...row },
      () => {
        if (row.pid) {
          refreshChildren(row.pid);
        } else {
          getTableList();
        }
      },
      isView
    );
  } else {
    editDrawerRef.value.acceptParams(
      { pid: 0, state: "Enable", sort: 0, leafFlag: 0 },
      getTableList,
      isView
    );
  }
};
const openAddChild = row => {
  editDrawerRef.value.acceptParams(
    { pid: row.id, state: "Enable", sort: 0, leafFlag: 0 },
    () => {
      refreshChildren(row.id);
    },
    false
  );
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.dictValue}】`);
  //getTableList();
  refreshChildren(row.pid);
};

const loadChildren = (row, treeNode, resolve) => {
  children(row.id).then(res => {
    resolve(res.data);
  });
};

const itemsDialogTitle = ref("");
const itemsDialogShow = ref(false);
const itemsLoading = ref(false);
const itemsDataList = ref([]);
const itemsTotal = ref(0);
const itemsSelectedData = ref([]);
const itemsParams = ref({
  pageNumber: 1,
  pageSize: 10,
  leafFlag: 1
});
const itemsColumns = [
  { label: "勾选列", type: "selection" },
  {
    prop: "dictKey",
    label: "字典值"
  },
  {
    prop: "dictValue",
    label: "字典标签"
  },
  {
    prop: "stateDesc",
    label: "状态"
  },
  {
    prop: "sort",
    label: "排序值"
  },
  {
    prop: "remark",
    label: "备注"
  },
  {
    label: "操作",
    //fixed: "right",
    slot: "operation",
    width: 210 //3个图标+按钮210
  }
];
const itemsDialogOpen = row => {
  itemsParams.value.pid = row.id;
  itemsDialogTitle.value = row.dictValue;
  itemsDialogShow.value = true;
  getItemsList();
};
const getItemsList = async () => {
  itemsLoading.value = true;
  const res = await page(itemsParams.value);
  itemsTotal.value = res.total;
  itemsDataList.value = res.data;
  itemsLoading.value = false;
};
/** 表格分页 */
function itemsCurrentChange(current) {
  itemsParams.value.pageNumber = current;
  getItemsList();
}
function itemsSizeChange(size) {
  itemsParams.value.pageSize = size;
  getItemsList();
}
/** 表格多选事件 */
function itemsSelectionChange(selected) {
  itemsSelectedData.value = selected;
}
const openItemsEdit = (row, isView) => {
  if (row && row.id) {
    editDrawerRef.value.acceptParams({ ...row }, getItemsList, isView);
  } else {
    editDrawerRef.value.acceptParams(
      { pid: itemsParams.value.pid, state: "Enable", sort: 0, leafFlag: 1 },
      getItemsList,
      isView
    );
  }
};
const itemsDelete = async row => {
  await useHandleData(del, row.id, `删除【${row.dictValue}】`);
  getItemsList();
};
/** 批量删除 */
const itemsDeleteBatch = async () => {
  const ids = itemsSelectedData.value.map(item => item.id);
  await useHandleData(dels, ids, `删除${ids.length}条选中数据`);
  itemsSelectedData.value = [];
  getItemsList();
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
