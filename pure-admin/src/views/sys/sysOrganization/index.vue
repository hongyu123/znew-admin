<template>
  <div class="table-box">
    <div v-show="isShowSearch" class="card table-search">
      <el-form inline @submit.prevent>
        <el-form-item label="部门名称">
          <el-input
            v-model="queryParam.name"
            placeholder="请输入部门名称查询"
            clearable
            @change="searchTreeTable"
          />
        </el-form-item>
        <el-form-item>
          <el-radio-group v-model="queryParam.state" @change="loadTreeData">
            <el-radio-button value="all">所有</el-radio-button>
            <el-radio-button value="Enable">启用</el-radio-button>
            <el-radio-button value="Disable">禁用</el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格内容 card -->
    <div class="card table-main">
      <!-- 表格头部 操作按钮 -->
      <div class="table-header">
        <div class="header-button-lf">
          <el-button
            type="primary"
            :icon="useRenderIcon(EpCirclePlus)"
            @click="openEdit()"
            >新增</el-button
          >
          <el-button
            type="primary"
            plain
            :icon="useRenderIcon(EpArrowDownBold)"
            @click="expand(tableData)"
            >展开</el-button
          >
          <el-button
            type="primary"
            plain
            :icon="useRenderIcon(EpArrowUpBold)"
            @click="collapse(tableData)"
            >折叠</el-button
          >
        </div>
        <div class="header-button-ri">
          <el-button
            :icon="useRenderIcon(EpRefresh)"
            circle
            @click="loadTreeData"
          />
          <el-button
            :icon="useRenderIcon(EpSearch)"
            circle
            @click="isShowSearch = !isShowSearch"
          />
        </div>
      </div>

      <el-table
        ref="treeTableRef"
        :data="tableData"
        style="width: 100%; height: 100%"
        row-key="id"
        border
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column prop="name" label="部门名称">
          <template #default="scope">
            <span v-if="scope.row.highLight" style="color: #f56c6c">{{
              scope.row.name
            }}</span>
            <span v-else>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型">
          <template #default="scope">
            <el-tag v-if="scope.row.type == 1" type="primary">组织机构</el-tag>
            <el-tag v-else type="info">部门</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" />
        <el-table-column prop="linkName" label="联系人" />
        <el-table-column prop="linkPhone" label="联系电话" />
        <el-table-column prop="state" label="状态">
          <template #default="scope">
            <el-tag v-if="scope.row.state == 'Enable'" type="success">
              启用
            </el-tag>
            <el-tag v-else type="info">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="220">
          <template #default="scope">
            <el-button
              type="primary"
              :icon="useRenderIcon(EpCirclePlus)"
              link
              @click="openEdit(scope.row, false)"
              >新增</el-button
            >
            <el-button
              type="primary"
              :icon="useRenderIcon(EpEditPen)"
              link
              @click="openEdit(scope.row, true)"
              >编辑</el-button
            >
            <el-button
              v-show="!scope.row.children || scope.row.children.length <= 0"
              type="danger"
              :icon="useRenderIcon(EpDelete)"
              link
              @click="delModel(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <EditDrawer ref="editDrawerRef" />
  </div>
</template>
<script setup lang="js">
import { onMounted, ref } from "vue";
import EpArrowDownBold from "@iconify-icons/ep/arrow-down-bold";
import EpArrowUpBold from "@iconify-icons/ep/arrow-up-bold";
import EpRefresh from "@iconify-icons/ep/refresh";
import EpSearch from "@iconify-icons/ep/search";
import EpCirclePlus from "@iconify-icons/ep/circle-plus";
import EpEditPen from "@iconify-icons/ep/edit-pen";
import EpDelete from "@iconify-icons/ep/delete";
import { useHandleData } from "@/hooks/useHandleData";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";

import { del, tree } from "./sysOrganization";
import EditDrawer from "./edit.vue";

/**
 * 菜单数据是比较少的,所以一次性加载全部数据.并且角色授权权限的时候也是要加载全部权限数据的
 * 如果数据量比较大则使用懒加载
 */
//加载数据
const tableData = ref([]);
onMounted(() => {
  loadTreeData();
});

const queryParam = ref({ state: "all" });
const loadTreeData = () => {
  const state =
    queryParam.value.state == "all" ? undefined : queryParam.value.state;
  tree(state).then(res => {
    tableData.value = res.data;
  });
};

//是否展示搜索条件
const isShowSearch = ref(true);

// 新增、查看、编辑
const editDrawerRef = ref();
const openEdit = async (row, edit) => {
  if (edit) {
    editDrawerRef.value.acceptParams({ ...row }, loadTreeData, false);
  } else {
    const defaultValue = { type: 1, state: "Enable", sort: 0 };
    if (row && row.id) {
      defaultValue.pid = row.id;
    }
    editDrawerRef.value.acceptParams(defaultValue, loadTreeData, false);
  }
};

//删除
const delModel = async params => {
  await useHandleData(del, params.id, `删除【${params.name}】`);
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
const searchTreeTable = value => {
  treeTableHighLight(value, tableData.value);
};
const treeTableHighLight = (value, list) => {
  let highLight = false;
  for (const k in list) {
    if (value && list[k].name.indexOf(value) >= 0) {
      list[k].highLight = true;
      highLight = true;
    } else {
      list[k].highLight = false;
    }
    if (list[k].children && list[k].children.length > 0) {
      if (treeTableHighLight(value, list[k].children)) {
        treeTableRef.value.toggleRowExpansion(list[k], true);
        return true;
      }
    }
  }
  return highLight;
};
</script>
