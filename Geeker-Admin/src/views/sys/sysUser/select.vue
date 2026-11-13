<template>
  <el-dialog
    v-model="dialogVisible"
    :title="dialogProps.title"
    :destroy-on-close="true"
    width="50%"
    draggable
    @close="handleClose"
  >
    <el-form :inline="true" :model="queryParams">
      <el-form-item label="账号">
        <el-input v-model.trim="queryParams.account_like" placeholder="请输入账号" clearable @keyup.enter="handleSearch" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model.trim="queryParams.nickname_like" placeholder="请输入昵称" clearable @keyup.enter="handleSearch" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleSearch">查询</el-button>
        <el-button :icon="Refresh" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table
      ref="selectTableRef"
      v-loading="tableLoading"
      highlight-current-row
      :data="tableData"
      height="400"
      style="width: 100%"
      @selection-change="handleSelectionChange"
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
      layout="total, sizes, prev, pager, next"
      :current-page="queryParams.pageNumber"
      :page-size="queryParams.pageSize"
      :total="total"
      :page-sizes="[10, 100, 500, 1000]"
      class="pagination"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="js" setup name="SysUserSelect">
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { Refresh, Search } from "@element-plus/icons-vue";
import { page } from "./api";

// 弹窗状态
const dialogVisible = ref(false);
const dialogProps = ref({ title: "选择用户" });
// 当前弹窗未完成的 Promise resolve
let pendingResolve = null;

// 表格数据
const selectTableRef = ref();
const tableLoading = ref(false);
const tableData = ref([]);
const total = ref(0);
const selectionRows = ref([]);

// 查询参数（withoutRoleId 用于过滤已授权该角色的用户）
const withoutRoleId = ref("");
const defaultQuery = () => ({
  pageNumber: 1,
  pageSize: 10
});
const queryParams = ref(defaultQuery());

const handleSelectionChange = rows => {
  selectionRows.value = rows;
};

// 获取表格数据
const getTableData = () => {
  tableLoading.value = true;
  const params = {};
  if (withoutRoleId.value) {
    params.withoutRoleId = withoutRoleId.value;
  }
  queryParams.value.params = params;
  page(queryParams.value)
    .then(res => {
      tableData.value = res.data || [];
      total.value = res.total || 0;
    })
    .finally(() => {
      tableLoading.value = false;
    });
};

// 查询
const handleSearch = () => {
  queryParams.value.pageNumber = 1;
  getTableData();
};

// 重置
const handleReset = () => {
  queryParams.value = defaultQuery();
  getTableData();
};

// 分页
const handleSizeChange = size => {
  queryParams.value.pageSize = size;
  queryParams.value.pageNumber = 1;
  getTableData();
};
const handleCurrentChange = pageNumber => {
  queryParams.value.pageNumber = pageNumber;
  getTableData();
};

// 关闭弹窗并结束当前 Promise（rows 为 null 表示取消）
const closeDialog = rows => {
  const resolve = pendingResolve;
  pendingResolve = null;
  dialogVisible.value = false;
  resolve?.(rows);
};

// 打开弹窗，返回 Promise<选中的用户列表 | null>
// options: { title 弹窗标题, withoutRoleId 需要过滤已授权的角色id }
const open = (options = {}) => {
  // 重复打开时先结束上一次未完成的 Promise
  closeDialog(null);
  dialogProps.value = { title: options.title || "选择用户" };
  withoutRoleId.value = options.withoutRoleId || "";
  queryParams.value = defaultQuery();
  tableData.value = [];
  total.value = 0;
  selectionRows.value = [];
  dialogVisible.value = true;
  getTableData();
  return new Promise(resolve => {
    pendingResolve = resolve;
  });
};

// 弹窗关闭（取消/右上角/遮罩/ESC）时返回 null
const handleClose = () => {
  closeDialog(null);
};

// 确认选择
const handleConfirm = () => {
  if (!selectionRows.value.length) {
    ElMessage.warning("请至少选择一个用户");
    return;
  }
  closeDialog([...selectionRows.value]);
};

defineExpose({ open });
</script>

<style scoped lang="scss">
.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 15px;
}
</style>
