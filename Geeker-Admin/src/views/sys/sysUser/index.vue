<template>
  <div class="main-box">
    <!-- 部门树筛选 -->
    <TreeFilter label="name" title="部门列表" :data="treeData" :default-value="initParam.orgId" @change="changeTreeFilter" />
    <div class="table-box">
      <ProTable
        ref="proTable"
        title="系统用户列表"
        :columns="columns"
        :request-api="getTableList"
        :init-param="initParam"
        colSettingKey="SysUser"
      >
        <!-- 表格 header 按钮 -->
        <template #tableHeader>
          <el-button type="primary" :icon="CirclePlus" @click="openEditForm()">新增</el-button>
        </template>
        <!-- 表格操作 -->
        <template #operation="scope">
          <el-button type="primary" link @click="handleResetPassword(scope.row)">重置密码</el-button>
          <el-button type="primary" icon="EditPen" link @click="openEditForm(scope.row)">编辑</el-button>
          <el-button type="danger" :icon="Delete" link @click="delModel(scope.row)">删除</el-button>
        </template>
      </ProTable>

      <SysUserDrawer ref="editFormRef" />
    </div>
  </div>
</template>

<script setup lang="jsx" name="useProTable">
import { onMounted, reactive, ref } from "vue";
import { Delete, CirclePlus } from "@element-plus/icons-vue";
import ProTable from "@/components/ProTable/index.vue";
import TreeFilter from "@/components/TreeFilter/index.vue";
import SysUserDrawer from "./edit.vue";

import { useHandleData } from "@/hooks/useHandleData";
import { enums } from "@/api/modules/common";
import { tree } from "../sysOrganization/api";
import { page, detail, del, resetPassword } from "./api";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 初始化请求参数（orgId 为空时查询全部用户）
const initParam = reactive({ orgId: "" });

// 部门树数据
const treeData = ref([]);
onMounted(async () => {
  const res = await tree();
  treeData.value = res.data || [];
});

// 部门树切换（val 为空字符串表示选中“全部”）
const changeTreeFilter = val => {
  proTable.value.pageable.pageNum = 1;
  initParam.orgId = val || "";
};

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = params => {
  // 未选择部门时不携带 orgId 参数，后端即查询全部用户
  if (!params.orgId) delete params.orgId;
  return page(params);
};

// 表格配置项
const columns = [
  {
    prop: "account",
    label: "账号",
    search: { el: "input", key: "account" }
  },
  {
    prop: "nickname",
    label: "昵称",
    search: { el: "input", key: "nickname" }
  },
  {
    prop: "phone",
    label: "手机号码",
    search: { el: "input", key: "phone" }
  },
  {
    prop: "avatar",
    label: "头像",
    render: scope => {
      return (
        <>
          <el-avatar shape="square" fit="contain" src={scope.row.avatar}></el-avatar>
        </>
      );
    }
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
    editFormRef.value.acceptParams(data, proTable.value.getTableList, isView);
  } else {
    // 新增时默认归属当前选中的部门
    editFormRef.value.acceptParams({ state: "Enable", orgId: initParam.orgId || undefined }, proTable.value.getTableList, isView);
  }
};

//删除
const delModel = async params => {
  await useHandleData(del, params.id, `删除【${params.account}】`);
  proTable.value.getTableList();
};

const handleResetPassword = async params => {
  await useHandleData(resetPassword, { id: params.id }, `重置密码【${params.account}】`);
};
</script>
