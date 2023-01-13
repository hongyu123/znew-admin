<template>
  <div class="table-box">
    <ProTable ref="proTable" title="系统示例表列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader="scope">
        <el-button type="primary" :icon="CirclePlus" @click="openEditForm('新增')" v-auth="['add']">新增</el-button>
        <el-button
          type="danger"
          :icon="Delete"
          plain
          @click="delsModel(scope.selectedListIds)"
          :disabled="!scope.isSelected"
          v-auth="['del']"
        >
          删除
        </el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="View" link @click="openEditForm('查看', scope.row)" v-auth="['detail']">查看</el-button>
        <el-button type="primary" icon="EditPen" link @click="openEditForm('编辑', scope.row)" v-auth="['edit']">编辑</el-button>
        <el-button type="danger" :icon="Delete" link @click="delModel(scope.row)" v-auth="['del']">删除</el-button>
      </template>
    </ProTable>

    <EditModelForm ref="editModelFormRef" />
  </div>
</template>

<script setup lang="tsx" name="SysDemo">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import EditModelForm from "@/views/sys/sysDemo/edit.vue";
import { Delete, CirclePlus } from "@element-plus/icons-vue";
import { page, save, edit, del, dels } from "@/api/sys/sysDemo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { GenderEnum } from "@/api/modules/enum";

//页面权限按钮
const { BUTTONS } = useAuthButtons();
// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  params.sortByField = params.sortByField ?? "id";
  params.sortByWay = params.sortByWay ?? "desc";
  return page(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  { type: "selection", fixed: "left", width: 50 },
  // {
  //   prop: "id",
  //   label: "ID"
  // },
  {
    prop: "name",
    label: "名称",
    search: { el: "input", key: "name" }
  },
  {
    prop: "gender",
    label: "性别",
    width: 110,
    search: { el: "select", key: "gender" },
    enum: GenderEnum
  },
  {
    prop: "state",
    label: "启用状态",
    width: 110,
    search: { el: "select", key: "state" },
    enum: [
      { label: "是", value: 1 },
      { label: "否", value: 0 }
    ]
  },
  {
    prop: "birth",
    label: "生日",
    width: 110,
    search: { el: "date-picker", key: "birth", props: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" } }
  },
  {
    prop: "registTime",
    label: "注册时间",
    width: 180,
    search: { el: "date-picker", key: "registTime", props: { format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" } }
  },
  {
    prop: "avatar",
    label: "头像",
    render: (scope: { row: any }) => {
      return BUTTONS.value("detail") ? <el-avatar shape="square" fit="contain" src={scope.row.avatar}></el-avatar> : "";
    }
  },
  {
    prop: "phone",
    label: "手机",
    width: 120,
    search: { el: "input", key: "phone" }
  },
  {
    prop: "location",
    label: "地址"
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
  await useHandleData(del, { id: params.id }, `删除【${params.name}】`);
  proTable.value.getTableList();
};

// 批量删除
const delsModel = async (ids: string[]) => {
  await useHandleData(dels, ids, "删除所选");
  proTable.value.clearSelection();
  proTable.value.getTableList();
};
</script>
