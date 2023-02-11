<template>
  <div class="table-box">
    <ProTable ref="proTable" title="app文章列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" :icon="CirclePlus" @click="openEditForm('新增')" v-auth="['add']">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="EditPen" link @click="openEditForm('编辑', scope.row)" v-auth="['edit']">编辑</el-button>
        <el-button
          v-show="scope.row.type != 'system'"
          type="danger"
          :icon="Delete"
          link
          @click="delModel(scope.row)"
          v-auth="['del']"
          >删除</el-button
        >
      </template>
    </ProTable>

    <EditModelForm ref="editModelFormRef" />
  </div>
</template>

<script setup lang="tsx" name="AppArticle">
import { ref } from "vue";
import { Delete, CirclePlus } from "@element-plus/icons-vue";
import { ColumnProps } from "@/components/ProTable/interface";
import { useHandleData } from "@/hooks/useHandleData";
import ProTable from "@/components/ProTable/index.vue";
import EditModelForm from "@/views/sys/appArticle/edit.vue";
import { page, add, edit, del } from "@/api/sys/appArticle";
import { AppArticleType } from "@/api/modules/enum";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  //params.sortByField = params.sortByField ?? "id";
  //params.sortByWay = params.sortByWay ?? "desc";
  return page(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  // {
  //   prop: "id",
  //   label: "ID"
  // },
  {
    prop: "type",
    label: "文章类型",
    width: 110,
    search: { el: "select", key: "type" },
    enum: AppArticleType
  },
  {
    prop: "title",
    label: "标题",
    search: { el: "input", key: "title_like" }
  },
  {
    prop: "introduction",
    label: "简介"
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
    api: title === "新增" ? add : title === "编辑" ? edit : "",
    getTableList: proTable.value.getTableList
  };
  editModelFormRef.value.acceptParams(params);
};

//删除
const delModel = async (params: any) => {
  await useHandleData(del, params.id, `删除【${params.title}】`);
  proTable.value.getTableList();
};
</script>
