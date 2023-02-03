<template>
  <div>
    <el-form :inline="true" :model="queryParams" class="demo-form-inline">
      <el-form-item label="标题">
        <el-input v-model="queryParams.title_like" />
      </el-form-item>
      <el-form-item>
        <el-button :icon="Search" circle type="primary" @click="searchData" />
      </el-form-item>
    </el-form>

    <el-table
      ref="selectTableRef"
      highlight-current-row
      :data="tableResult.tableData"
      style="width: 100%"
      @current-change="handleSingleSelect"
    >
      <el-table-column property="title" label="标题" />
      <el-table-column label="图片">
        <template #default="scope">
          <el-avatar shape="square" fit="contain" :src="scope.row.picture"></el-avatar>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="sizes, prev, pager, next"
      v-model:current-page="queryParams.pageNumber"
      v-model:page-size="queryParams.pageSize"
      :total="tableResult.total"
      :page-sizes="[10, 100, 500, 1000]"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts" setup name="TeacherSelect">
import { onMounted, ref } from "vue";
import { ElTable } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { page } from "@/api/sys/appArticle";

interface SelectProps {
  handleSingleSelect: (params: any) => void;
}
// 接受父组件参数
const props = withDefaults(defineProps<SelectProps>(), {});

const handleSingleSelect = (val: any) => {
  props.handleSingleSelect(val);
};

const tableResult = ref<any>({});
onMounted(() => {
  searchData();
});

const queryParams = ref<any>({});
const handleSizeChange = (val: number) => {
  queryParams.value.pageSize = val;
};
const handleCurrentChange = (val: number) => {
  queryParams.value.pageNumber = val;
};
const searchData = () => {
  page(queryParams.value).then(res => {
    tableResult.value.total = res.total;
    tableResult.value.tableData = res.data;
  });
};
</script>
