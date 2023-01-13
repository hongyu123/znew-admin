<template>
  <div class="table-box">
    <ProTable ref="proTable" title="系统用户列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" :icon="CirclePlus" @click="openEditForm('新增')">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" link @click="handleResetPassword(scope.row)">重置密码</el-button>
        <el-button type="primary" icon="EditPen" link @click="openEditForm('编辑', scope.row)">编辑</el-button>
        <el-button type="danger" :icon="Delete" link @click="delModel(scope.row)">删除</el-button>
      </template>
    </ProTable>

    <EditModelForm ref="editModelFormRef" />
  </div>
</template>

<script setup lang="tsx" name="useProTable">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import EditModelForm from "@/views/sys/sysUser/edit.vue";
import { Delete, CirclePlus } from "@element-plus/icons-vue";
import { page, save, edit, del, resetPassword } from "@/api/sys/sysUser";
import { useHandleData } from "@/hooks/useHandleData";
import { EnableStateEnum } from "@/api/modules/enum";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  return page(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  {
    prop: "account",
    label: "账号",
    search: { el: "input", key: "account_like" }
  },
  {
    prop: "nickname",
    label: "昵称",
    search: { el: "input", key: "nickname_like" }
  },
  {
    prop: "phone",
    label: "手机号码",
    search: { el: "input", key: "phone_like" }
  },
  {
    prop: "avatar",
    label: "头像",
    render: (scope: { row: any }) => {
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
    enum: EnableStateEnum,
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
const editModelFormRef = ref();
const openEditForm = (title: string, rowData: Partial<any> = {}) => {
  let params = {
    title,
    rowData: { ...rowData },
    isView: title === "查看",
    api: title === "新增" ? save : title === "编辑" ? edit : "",
    getTableList: proTable.value.getTableList
  };
  editModelFormRef.value.acceptParams(params);
};

//删除
const delModel = async (params: any) => {
  await useHandleData(del, { id: params.id }, `删除【${params.account}】`);
  proTable.value.getTableList();
};

const handleResetPassword = async (params: any) => {
  await useHandleData(resetPassword, { id: params.id }, `重置密码【${params.account}】`);
};
</script>
