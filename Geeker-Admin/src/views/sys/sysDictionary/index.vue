<template>
  <div class="table-box">
    <!-- 查询表单 -->
    <div class="card table-search" v-show="isShowSearch">
      <el-form inline :model="queryParam" @submit.prevent="handleSearch">
        <el-form-item label="字典类型">
          <el-input v-model.trim="queryParam.dictKey" placeholder="请输入字典类型" clearable />
        </el-form-item>
        <el-form-item label="字典名称">
          <el-input v-model.trim="queryParam.dictValue" placeholder="请输入字典名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" native-type="submit">查询</el-button>
          <el-button icon="Refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 表格内容 card -->
    <div class="card table-main">
      <!-- 表格头部 操作按钮 -->
      <div class="table-header">
        <div class="header-button-lf">
          <el-button type="primary" icon="CirclePlus" @click="openEditForm({ pid: 0 })">新增</el-button>
        </div>
        <div class="header-button-ri">
          <el-button icon="Refresh" circle @click="getTableList" />
          <el-button icon="Search" circle @click="isShowSearch = !isShowSearch" />
        </div>
      </div>

      <!-- 表格主体（懒加载树形表格） -->
      <el-table
        ref="tableRef"
        v-loading="loading"
        :data="tableData"
        style="width: 100%"
        row-key="id"
        border
        lazy
        :load="loadChildren"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column prop="dictKey" label="字典类型" min-width="200">
          <template #default="scope">
            <el-link type="primary" @click="openDictDialog(scope.row.id)">{{ scope.row.dictKey }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="dictValue" label="字典名称" min-width="200" />
        <el-table-column prop="state" label="状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.state === 'Enable'" type="success">启用</el-tag>
            <el-tag v-else type="info">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序值" width="100" />
        <el-table-column prop="remark" label="备注" min-width="200" />
        <el-table-column label="操作" fixed="right" width="220">
          <template #default="{ row }">
            <el-button type="primary" icon="CirclePlus" link @click="openEditForm({ pid: row.id })">新增</el-button>
            <el-button type="primary" icon="EditPen" link @click="openEditForm(row)">编辑</el-button>
            <el-button v-if="!row.hasChildren" type="danger" icon="Delete" link @click="delModel(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="queryParam.pageNumber"
        :page-size="queryParam.pageSize"
        :page-sizes="[10, 25, 50, 100]"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <SysDictionaryDrawer ref="editFormRef" />

    <el-dialog v-model="dictVisible" title="字典项" size="30%">
      <el-button type="primary" icon="CirclePlus" @click="openEditDict({ pid: dictPid })">新增</el-button>
      <el-button type="danger" icon="Delete" plain :disabled="selectIds.length <= 0" @click="delsDict"> 删除 </el-button>
      <el-table :data="dictList" style="width: 100%" @selection-change="dictSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="dictKey" label="字典值" />
        <el-table-column prop="dictValue" label="字典标签" />
        <el-table-column prop="stateDesc" label="状态" />
        <el-table-column prop="sort" label="排序值" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" icon="EditPen" link @click="openEditDict(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" link @click="delDict(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <el-button @click="dictVisible = false">取消</el-button>
        <el-button type="primary" @click="dictVisible = false"> 关闭 </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="js" name="SysDictionary">
import { onMounted, ref } from "vue";

import SysDictionaryDrawer from "./edit.vue";
import { useHandleData } from "@/hooks/useHandleData";
import { page, children, del, dels } from "./api.js";

// 查询参数（含分页）
const defaultQuery = () => ({ pageNumber: 1, pageSize: 10, dictKey: "", dictValue: "" });
const queryParam = ref(defaultQuery());
// 是否展示搜索条件
const isShowSearch = ref(true);

// 表格数据
const tableRef = ref();
const loading = ref(false);
const tableData = ref([]);
const total = ref(0);

onMounted(() => {
  getTableList();
});

// 获取表格数据
const getTableList = () => {
  loading.value = true;
  page(queryParam.value)
    .then(res => {
      tableData.value = res.data || [];
      total.value = res.total || 0;
    })
    .finally(() => {
      loading.value = false;
    });
};

// 查询
const handleSearch = () => {
  queryParam.value.pageNumber = 1;
  getTableList();
};

// 重置
const handleReset = () => {
  queryParam.value = defaultQuery();
  getTableList();
};

// 分页
const handleSizeChange = size => {
  queryParam.value.pageSize = size;
  queryParam.value.pageNumber = 1;
  getTableList();
};
const handleCurrentChange = pageNumber => {
  queryParam.value.pageNumber = pageNumber;
  getTableList();
};

// 懒加载子节点
const loadChildren = (row, treeNode, resolve) => {
  children(row.id, 0).then(res => {
    resolve(res.data || []);
  });
};

// 刷新指定父节点下已加载的子节点列表
const updateChildren = id => {
  children(id, 0).then(res => {
    tableRef.value?.updateKeyChildren(id, res.data || []);
  });
};

// 新增、查看、编辑
const editFormRef = ref();
const openEditForm = async (row, isView = false) => {
  const refreshTable = () => {
    // 刷新根列表,同步 hasChildren 等字段
    getTableList();
    // 子节点变更时,同步刷新父节点下已加载的子节点
    if (row && row.pid != 0) updateChildren(row.pid);
  };
  if (row && row.id) {
    editFormRef.value.acceptParams({ ...row }, refreshTable, isView);
  } else {
    editFormRef.value.acceptParams({ state: "Enable", pid: row.pid, leafFlag: 0 }, refreshTable, isView);
  }
};

// 删除
const delModel = async row => {
  await useHandleData(del, row.id, `删除【${row.dictKey}】`);
  getTableList();
  if (row && row.pid != 0) updateChildren(row.pid);
};

// 字典项弹窗
const dictVisible = ref(false);
const dictPid = ref(0);
const dictList = ref([]);
const selectIds = ref([]);
const openDictDialog = pid => {
  children(pid, 1).then(res => {
    dictList.value = res.data || [];
    dictPid.value = pid;
    dictVisible.value = true;
  });
};
const openEditDict = async (row, isView = false) => {
  const refreshTable = () => {
    openDictDialog(row.pid);
  };
  if (row && row.id) {
    editFormRef.value.acceptParams({ ...row }, refreshTable, isView);
  } else {
    editFormRef.value.acceptParams({ state: "Enable", pid: row.pid, leafFlag: 1 }, refreshTable, isView);
  }
};
const dictSelectionChange = val => {
  selectIds.value = val.map(item => item.id);
};

// 删除字典项
const delDict = async row => {
  await useHandleData(del, row.id, `删除【${row.dictKey}】`);
  openDictDialog(row.pid);
};

// 批量删除字典项
const delsDict = async () => {
  const ids = selectIds.value;
  await useHandleData(dels, ids, `删除${ids.length}条选中数据`);
  openDictDialog(dictPid.value);
};
</script>
