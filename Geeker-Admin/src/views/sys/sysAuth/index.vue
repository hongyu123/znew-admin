<template>
  <div class="table-box">
    <div class="card table-search" v-show="isShowSearch">
      <el-form inline @submit.prevent="searchTreeTable">
        <el-form-item label="名称">
          <el-input v-model.trim="queryParam.name" placeholder="请输入名称查询" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-radio-group v-model="queryParam.state" @change="loadTreeData">
            <el-radio-button label="所有" value="all"></el-radio-button>
            <el-radio-button label="启用" value="Enable"></el-radio-button>
            <el-radio-button label="禁用" value="Disable"></el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格内容 card -->
    <div class="card table">
      <!-- 表格头部 操作按钮 -->
      <div class="table-header">
        <div class="header-button-lf">
          <el-button type="primary" icon="CirclePlus" @click="openEditForm({})">新增</el-button>
          <el-button type="primary" plain icon="ArrowDownBold" @click="expand(tableData)">展开</el-button>
          <el-button type="primary" plain icon="ArrowUpBold" @click="collapse(tableData)">折叠</el-button>
        </div>
        <div class="header-button-ri">
          <el-button icon="Refresh" circle @click="loadTreeData"> </el-button>
          <el-button icon="Search" circle @click="isShowSearch = !isShowSearch"> </el-button>
        </div>
      </div>

      <el-table
        ref="treeTableRef"
        :data="tableData"
        style="width: 100%"
        row-key="id"
        border
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column prop="title" label="标题">
          <template #default="scope">
            <span v-if="scope.row.highLight" class="keyword-highlight">{{ scope.row.title }}</span>
            <span v-else>{{ scope.row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="icon" label="菜单图标">
          <template #default="scope">
            <el-icon :size="20">
              <component v-if="scope.row.icon" :is="scope.row.icon"></component>
              <span v-else></span>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="authTypeDesc" label="类型" />
        <el-table-column prop="sort" label="排序" />
        <el-table-column prop="path" label="路由" />
        <el-table-column prop="showFlag" label="是否显示">
          <template #default="scope">
            <el-tag v-if="scope.row.showFlag == 1">显示</el-tag>
            <el-tag v-else type="info">影藏</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="state" label="状态">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.state == 'Enable'">启用</el-tag>
            <el-tag v-else type="info">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="220">
          <template #default="scope">
            <el-button type="primary" icon="CirclePlus" link @click="openEditForm({ parentId: scope.row.id })">新增</el-button>
            <el-button type="primary" icon="EditPen" link @click="openEditForm(scope.row)">编辑</el-button>
            <el-button
              v-show="!scope.row.children || scope.row.children.length <= 0"
              type="danger"
              icon="Delete"
              link
              @click="delModel(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <SysAuthDrawer ref="editFormRef" />
  </div>
</template>
<script setup lang="js">
import { onMounted, ref } from "vue";
import { detail, del, tree } from "./api";
import SysAuthDrawer from "./edit.vue";
import { useHandleData } from "@/hooks/useHandleData";

/**
 * 菜单数据是比较少的,所以一次性加载全部数据.并且角色授权权限的时候也是要加载全部权限数据的
 * 如果数据量比较大则使用懒加载
 */

const queryParam = ref({ state: "all" });
const tableData = ref([]);
//是否展示搜索条件
const isShowSearch = ref(false);

onMounted(() => {
  loadTreeData();
});

const loadTreeData = () => {
  const state = queryParam.value.state == "all" ? "" : queryParam.value.state;
  tree(state).then(res => {
    tableData.value = res.data;
  });
};

// 新增、查看、编辑
const editFormRef = ref();
const openEditForm = async (row, isView = false) => {
  if (row && row.id) {
    const { data } = await detail(row.id);
    editFormRef.value.acceptParams(data, loadTreeData, isView);
  } else {
    row.authType = "Menu";
    row.showFlag = 1;
    row.cacheFlag = 1;
    row.frameFlag = 0;
    row.state = "Enable";
    editFormRef.value.acceptParams(row, loadTreeData, isView);
  }
};

//删除
const delModel = async params => {
  await useHandleData(del, params.id, `删除【${params.name}】`);
  //proTable.value.getTableList();
  delTreeData(params.id, tableData.value);
};
const delTreeData = (id, list) => {
  for (let i = 0; i < list.length; i++) {
    if (list[i].id == id) {
      list.splice(i, 1);
      return true;
    }
    if (list[i].children && list[i].children.length > 0) {
      if (delTreeData(id, list[i].children)) {
        return true;
      }
    }
  }
};

const treeTableRef = ref();
//展开
const expand = list => {
  for (const k in list) {
    if (list[k].children && list[k].children.length > 0) {
      treeTableRef.value.toggleRowExpansion(list[k], true);
      expand(list[k].children);
    }
  }
};
//折叠
const collapse = list => {
  for (const k in list) {
    if (list[k].children && list[k].children.length > 0) {
      treeTableRef.value.toggleRowExpansion(list[k], false);
      expand(list[k].children);
    }
  }
};

//高亮搜索
const searchTreeTable = e => {
  treeTableHighLight(queryParam.value.name, tableData.value);
};
const treeTableHighLight = (value, list) => {
  if (!value) {
    return false;
  }
  for (const node of list) {
    if (node.title.includes(value)) {
      node.highLight = true;
      return true;
    } else if (node.children && node.children.length > 0) {
      if (treeTableHighLight(value, node.children)) {
        treeTableRef.value.toggleRowExpansion(node, true);
        return true;
      }
    }
  }
  return false;
};
</script>

<style scoped>
.keyword-highlight {
  font-weight: 600;
  color: var(--el-color-danger);
  background-color: var(--el-color-warning-light-8);
}
</style>
