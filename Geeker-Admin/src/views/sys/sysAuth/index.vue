<template>
  <div class="table-box">
    <div class="card table-search" v-show="isShowSearch">
      <el-form inline>
        <el-form-item label="名称">
          <el-input v-model="queryParam.name" placeholder="请输入名称查询" clearable @change="searchTreeTable"></el-input>
        </el-form-item>
        <el-form-item>
          <el-radio-group v-model="queryParam.state" @change="loadTreeData">
            <el-radio-button label="all">所有</el-radio-button>
            <el-radio-button label="Enable">启用</el-radio-button>
            <el-radio-button label="Disable">禁用</el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格内容 card -->
    <div class="card table">
      <!-- 表格头部 操作按钮 -->
      <div class="table-header">
        <div class="header-button-lf">
          <el-button type="primary" :icon="CirclePlus" @click="openEditForm('新增')">新增</el-button>
          <el-button type="primary" plain :icon="CirclePlus" @click="expand(tableData)">展开</el-button>
          <el-button type="primary" plain :icon="CirclePlus" @click="collapse(tableData)">折叠</el-button>
        </div>
        <div class="header-button-ri">
          <el-button :icon="Refresh" circle @click="loadTreeData"> </el-button>
          <el-button :icon="Search" circle @click="isShowSearch = !isShowSearch"> </el-button>
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
        <el-table-column prop="name" label="标题">
          <template #default="scope">
            <span v-if="scope.row.highLight" style="color: #f56c6c">{{ scope.row.name }}</span>
            <span v-else>{{ scope.row.name }}</span>
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
        <el-table-column prop="webCode" label="名称" />
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
            <el-tag v-if="scope.row.state == 'Enable'">启用</el-tag>
            <el-tag v-else type="info">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="220">
          <template #default="scope">
            <el-button type="primary" :icon="CirclePlus" link @click="openEditForm('新增', scope.row)">新增</el-button>
            <el-button type="primary" icon="EditPen" link @click="openEditForm('编辑', scope.row)">编辑</el-button>
            <el-button
              v-show="!scope.row.children || scope.row.children.length <= 0"
              type="danger"
              :icon="Delete"
              link
              @click="delModel(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <EditModelForm ref="editModelFormRef" />
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref } from "vue";
import { CirclePlus, Delete, Refresh, Search } from "@element-plus/icons-vue";
import { save, edit, del, tree } from "@/api/sys/sysAuth";
import EditModelForm from "@/views/sys/sysAuth/edit.vue";
import { useHandleData } from "@/hooks/useHandleData";

/**
 * 菜单数据是比较少的,所以一次性加载全部数据.并且角色授权权限的时候也是要加载全部权限数据的
 * 如果数据量比较大则使用懒加载
 */
//加载数据
const tableData = ref<any>([]);
onMounted(() => {
  loadTreeData();
});

const queryParam = ref<any>({ state: "all" });
const loadTreeData = () => {
  const state = queryParam.value.state == "all" ? null : queryParam.value.state;
  tree({ state }).then((res: any) => {
    tableData.value = res.data;
  });
};

//是否展示搜索条件
const isShowSearch = ref(false);

// const load = (row: any, treeNode: unknown, resolve: (date: any[]) => void) => {
//   children(row.id).then((res: any) => {
//     resolve(res.data);
//   });
// };

// 新增、查看、编辑
const editModelFormRef = ref();
const openEditForm = (title: string, rowData: Partial<any> = {}) => {
  if (title === "新增" && rowData?.id) {
    let params = {
      title,
      rowData: { parentId: rowData.id },
      isView: false,
      api: save,
      getTableList: loadTreeData
    };
    editModelFormRef.value.acceptParams(params);
    return false;
  }
  let params = {
    title,
    rowData: { ...rowData },
    isView: title === "查看",
    api: title === "新增" ? save : title === "编辑" ? edit : "",
    getTableList: loadTreeData
  };
  editModelFormRef.value.acceptParams(params);
};

//删除
const delModel = async (params: any) => {
  await useHandleData(del, { id: params.id }, `删除【${params.name}】`);
  //proTable.value.getTableList();
  delTreeData(params.id, tableData.value);
};
const delTreeData = (id: number, list: any) => {
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
const expand = (list: any) => {
  for (const k in list) {
    if (list[k].children && list[k].children.length > 0) {
      treeTableRef.value.toggleRowExpansion(list[k], true);
      expand(list[k].children);
    }
  }
};
//折叠
const collapse = (list: any) => {
  for (const k in list) {
    if (list[k].children && list[k].children.length > 0) {
      treeTableRef.value.toggleRowExpansion(list[k], false);
      expand(list[k].children);
    }
  }
};

//高亮搜索
const searchTreeTable = (value: string | number) => {
  treeTableHighLight(value, tableData.value);
};
const treeTableHighLight = (value: string | number, list: any) => {
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
