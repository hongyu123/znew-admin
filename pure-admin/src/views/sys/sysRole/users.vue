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
      @search="handleSearch"
      @reset="handleReset"
    />

    <PureTableBar
      title="角色授权用户"
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
          取消授权
        </el-button>
        <el-button
          type="primary"
          :icon="useRenderIcon(EpCirclePlus)"
          @click="openEdit()"
        >
          添加授权
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
              class="reset-margin"
              link
              type="danger"
              :size="size"
              @click="handleDelete(row)"
            >
              取消授权
            </el-button>
          </template>
        </pure-table>
      </template>
    </PureTableBar>

    <el-dialog
      v-model="selectDataVisible"
      :destroy-on-close="true"
      width="50%"
      title="选择用户"
      draggable
    >
      <SysUserSelect ref="selectDataRef" :hasNoRoleId="currentRoleId" />
      <template #footer>
        <el-button @click="selectDataVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSelectData">确定</el-button>
      </template>
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
import { useRoute } from "vue-router";
import { PlusSearch } from "plus-pro-components";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { useHandleData } from "@/hooks/useHandleData";
import { useQueryTable } from "@/hooks/useQueryTable";

import SysUserSelect from "../sysUser/select.vue";
import { users, allocateUsers, cancelUsers } from "./sysRole";

const route = useRoute();
const currentRoleId = ref(route.params.roleId);
onMounted(() => {
  getTableList();
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
  }
]);

// const inputColumns = [
//   "params.account_like",
//   "params.nickname_like",
//   "params.phone_like"
// ];
// /** 搜索表单字段变化处理 */
// const handleSearchChange = (values, column) => {
//   if (inputColumns.indexOf(column.prop) < 0) {
//     getTableList();
//   }
// };

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
    label: "操作",
    fixed: "right",
    slot: "operation"
  }
];

/** 表格获取数据 */
const getTableList = async () => {
  queryParams.value.roleId = route.params.roleId;
  loading.value = true;
  const res = await users(queryParams.value);
  total.value = res.total;
  dataList.value = res.data;
  loading.value = false;
};

const openEdit = () => {
  selectDataVisible.value = true;
};

/** 删除 */
const handleDelete = async row => {
  await useHandleData(
    cancelUsers,
    { roleId: route.params.roleId, userIds: [row.id] },
    `取消授权用户【${row.account}】`
  );
  getTableList();
};

/** 批量删除 */
const handleDeleteBatch = async () => {
  const ids = selectedData.value.map(item => item.id);
  await useHandleData(
    cancelUsers,
    { roleId: route.params.roleId, userIds: ids },
    `取消授权${ids.length}个选中用户`
  );
  selectedData.value = [];
  tableRef.value.getTableRef().clearSelection();
  getTableList();
};

//选择弹出框
const selectDataVisible = ref(false);
const selectDataRef = ref();
const handleSelectData = async () => {
  const selectData = selectDataRef.value.getSelected();
  const ids = selectData.map(item => item.id);
  await allocateUsers({ roleId: route.params.roleId, userIds: ids });
  selectDataVisible.value = false;
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
