<template>
  <div class="table-box">
    <ProTable ref="proTable" title="代码生成列表" :columns="columns" :requestApi="getTableList" selectId="tableName">
      <!-- 表格 header 按钮 -->
      <template #tableHeader="scope">
        <el-button type="primary" @click="handleBatchGenToPath(scope.selectedListIds)">配置路径生成</el-button>
        <el-button type="primary" @click="handleBatchGenToProject(scope.selectedListIds)">项目路径生成</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="View" link @click="preview(scope.row)">预览</el-button>
        <el-button type="primary" link @click="handleGenToPath(scope.row)">配置路径生成</el-button>
        <el-button type="primary" link @click="handleGenToProject(scope.row)">项目路径生成</el-button>
        <el-button type="primary" link @click="formGen(scope.row)">表单生成</el-button>
      </template>
    </ProTable>

    <JavaPreview ref="javaPreviewRef" />
  </div>
</template>

<script setup lang="tsx" name="AppVersion">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import JavaPreview from "@/views/sys/gen/JavaPreview.vue";
import { tablePage, genToPath, genToProject, batchGenToPath, batchGenToProject } from "@/api/sys/gen";
import { ElMessage } from "element-plus";
import { useHandleData } from "@/hooks/useHandleData";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  return tablePage(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  { type: "selection", fixed: "left", width: 50 },
  {
    prop: "tableName",
    label: "表名",
    search: { el: "input" }
  },
  {
    prop: "tableComment",
    label: "注释"
  },
  { prop: "operation", label: "操作", fixed: "right", width: 400 }
];

//配置路径生成
const handleGenToPath = async (row: any) => {
  const res = await genToPath({ tableName: row.tableName });
  ElMessage({
    type: "success",
    message: res.message
  });
};

const handleGenToProject = async (row: any) => {
  const res = await genToProject({ tableName: row.tableName });
  ElMessage({
    type: "success",
    message: res.message
  });
};

// 批量生成
const handleBatchGenToPath = async (ids: string[]) => {
  await useHandleData(batchGenToPath, ids, "配置路径批量生成");
};
const handleBatchGenToProject = async (ids: string[]) => {
  await useHandleData(batchGenToProject, ids, "项目路径批量生成");
};

//表单生成
const router = useRouter();
const formGen = (row: any) => {
  router.push(`/sys/formGen?tableName=${row.tableName}`);
};

//预览
const javaPreviewRef = ref();
const preview = (row: any) => {
  javaPreviewRef.value.acceptParams(row.tableName);
};
</script>
