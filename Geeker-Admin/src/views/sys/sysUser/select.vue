<template>
  <div>
    <el-form :inline="true" :model="queryParams" class="demo-form-inline">
      <el-form-item label="账号">
        <el-input v-model="queryParams.account_like" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="queryParams.nickname_like" />
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
      @selection-change="handleSelectionChange"
      @current-change="handleSingleSelect"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column property="account" label="账号" />
      <el-table-column property="nickname" label="昵称" />
      <el-table-column property="phone" label="手机号码" />
      <el-table-column label="头像">
        <template #default="scope">
          <el-avatar shape="square" fit="contain" :src="scope.row.photo"></el-avatar>
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
import { page } from "@/api/sys/sysUser";

const selectTableRef = ref<InstanceType<typeof ElTable>>();
const multipleSelection = ref();
const handleSelectionChange = (val: any) => {
  multipleSelection.value = val;
};
const handleSingleSelect = (val: any) => {
  console.log(val);
};
const getSelect = () => {
  return selectTableRef.value?.getSelectionRows();
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

defineExpose({ getSelect });
</script>
