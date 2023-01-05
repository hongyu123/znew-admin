<template>
  <div class="table-box">
    <ProTable
      ref="proTable"
      title="表单生成记录"
      :columns="columns"
      :requestApi="getTableList"
      selectId="tableName"
      :toolButton="false"
    >
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" link @click="preview(scope.row)">预览</el-button>
        <el-button type="primary" link @click="formGen(scope.row)">表单生成</el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="AppVersion">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import { page } from "@/api/sys/gen";

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
    prop: "tableName",
    label: "表名",
    search: { el: "input" }
  },
  {
    prop: "tableRemark",
    label: "注释"
  },
  { prop: "operation", label: "操作", fixed: "right", width: 200 }
];

//预览
const preview = async (row: any) => {
  console.log(row);
};

//表单生成
const router = useRouter();
const formGen = (row: any) => {
  router.push(`/sys/formGen?id=${row.id}`);
};
</script>
