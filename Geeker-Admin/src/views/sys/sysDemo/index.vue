<template>
  <div class="table-box">
    <ProTable ref="proTable" title="系统示例表" :columns="columns" :request-api="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader="scope">
        <el-button type="primary" icon="CirclePlus" @click="openEditForm()" v-auth="['add']">新增</el-button>
        <el-button type="primary" icon="Upload" plain @click="importFile" v-auth="['import']">导入</el-button>
        <el-button type="primary" icon="Download" plain @click="exportFile" v-auth="['export']">导出</el-button>
        <el-button
          type="danger"
          icon="Delete"
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
        <el-button type="primary" icon="View" link @click="openEditForm(scope.row, true)" v-auth="['view']">查看</el-button>
        <el-button type="primary" icon="EditPen" link @click="openEditForm(scope.row)" v-auth="['edit']">编辑</el-button>
        <el-button type="danger" icon="Delete" link @click="delModel(scope.row)" v-auth="['del']">删除</el-button>
      </template>
    </ProTable>

    <SysDemoDrawer ref="editFormRef" />
    <ImportExcel ref="importRef" />
  </div>
</template>

<script setup lang="jsx" name="SysDemo">
import { ref } from "vue";
import ProTable from "@/components/ProTable/index.vue";
import SysDemoDrawer from "./edit.vue";
import ImportExcel from "@/components/ImportExcel/index.vue";
import { page, detail, del, dels, exp, imp } from "./api";
import { useHandleData } from "@/hooks/useHandleData";
import { GenderEnum } from "@/enums/commonEnum";
import { enums } from "@/api/modules/common";
import { useDownload, directDownload } from "@/hooks/useDownload";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = params => {
  if (params.registTimeArr) {
    params.params = { registTimeArr: params.registTimeArr.join(",") };
  }
  return page(params);
};

// 表格配置项
const columns = [
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
    search: {
      el: "date-picker",
      key: "registTimeArr",
      props: { type: "daterange", format: "YYYY-MM-DD", valueFormat: "YYYY-MM-DD" }
    }
  },
  {
    prop: "avatar",
    label: "头像",
    render: scope => {
      return <el-avatar shape="square" fit="contain" src={scope.row.avatarUrl || scope.row.avatar}></el-avatar>;
    }
  },
  {
    prop: "phone",
    label: "手机",
    width: 120,
    search: { el: "input", key: "phone" }
  },
  // {
  //   prop: "location",
  //   label: "地址"
  // },
  { prop: "operation", label: "操作", fixed: "right", width: 250 }
];

// 新增、查看、编辑
const editFormRef = ref();
const openEditForm = async (row, isView = false) => {
  if (row && row.id) {
    const { data } = await detail(row.id);
    editFormRef.value.acceptParams(data, proTable.value.getTableList, isView);
  } else {
    editFormRef.value.acceptParams({ state: "Enable" }, proTable.value.getTableList, isView);
  }
};

//删除
const delModel = async params => {
  await useHandleData(del, params.id, `删除【${params.name}】`);
  proTable.value.getTableList();
};

// 批量删除
const delsModel = async ids => {
  await useHandleData(dels, ids, `删除${ids.length}条选中数据`);
  proTable.value.clearSelection();
  proTable.value.getTableList();
};

// 导出
const exportFile = async () => {
  directDownload("/sysDemo/export", proTable.value.searchParam);
  //useDownload(exp, "导出示例", proTable.value.searchParam);
};
// 导入
const importRef = ref();
const importFile = () => {
  let params = {
    title: "导入示例",
    template: "SysDemo",
    tempApi: exp,
    importApi: imp,
    getTableList: proTable.value.getTableList
  };
  importRef.value.acceptParams(params);
};
</script>
