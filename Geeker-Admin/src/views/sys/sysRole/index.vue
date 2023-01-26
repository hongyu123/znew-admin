<template>
  <div class="table-box">
    <ProTable ref="proTable" title="系统角色列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" :icon="CirclePlus" @click="openEditForm('新增')">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" link @click="toUserRole(scope.row)">用户</el-button>
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
import EditModelForm from "@/views/sys/sysRole/edit.vue";
import { Delete, CirclePlus } from "@element-plus/icons-vue";
import { page, save, edit, del } from "@/api/sys/sysRole";
import { useHandleData } from "@/hooks/useHandleData";
import { EnableStateEnum } from "@/api/modules/enum";
import { useRouter } from "vue-router";

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
    prop: "name",
    label: "角色",
    search: { el: "input", key: "name_like" }
  },
  {
    prop: "code",
    label: "编码",
    search: { el: "input", key: "code_like" }
  },
  {
    prop: "sort",
    label: "排序",
    sortable: "sort"
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

  { prop: "operation", label: "操作", fixed: "right", width: 200 }
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
  await useHandleData(del, params.id, `删除【${params.name}】`);
  proTable.value.getTableList();
};

//跳转授权用户
const router = useRouter();
const toUserRole = (params: any) => {
  router.push(`/sys/sysUserRole/${params.id}`);
};
</script>
