<template>
  <el-drawer
    v-model="drawerProps.visible"
    class="plus-drawer-form"
    :destroy-on-close="true"
    size="50%"
    :title="drawerProps.title"
  >
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="角色名" prop="name" class="plus-form-item">
        <el-input
          v-model="row.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写角色名"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="角色编码" prop="code" class="plus-form-item">
        <el-input
          v-model="row.code"
          maxlength="50"
          show-word-limit
          placeholder="请填写角色编码"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state" class="plus-form-item-field">
          <el-radio
            v-for="item in stateEnums"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序" prop="sort" class="plus-form-item">
        <el-input-number
          v-model="row.sort"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="权限">
        <el-tree
          ref="authTree"
          node-key="id"
          :props="{
            label: 'name',
            children: 'children',
            class: customNodeClass
          }"
          :data="authTreeData"
          show-checkbox
          default-expand-all
        />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="checkedAll" @change="handleCheckedAllTree"
          >全选</el-checkbox
        >
      </el-form-item>
      <el-form-item label="备注" prop="remark" class="plus-form-item">
        <el-input
          v-model="row.remark"
          maxlength="255"
          show-word-limit
          placeholder="请填写备注"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button
        v-show="!drawerProps.isView"
        type="primary"
        @click="handleSubmit"
        >确定</el-button
      >
    </template>
  </el-drawer>
</template>

<script setup name="EditDrawer">
import "plus-pro-components/es/components/drawer-form/style/css";
import { ref, reactive, onMounted, nextTick } from "vue";
import { ElMessage } from "element-plus";

import { enums } from "@/api/sys/common";
import { add, edit } from "./sysRole";
import { tree } from "../sysAuth/sysAuth";

const stateEnums = ref([]);
onMounted(() => {
  enums("EnableState").then(res => {
    stateEnums.value = res.data;
  });
  tree("Enable").then(res => {
    authTreeData.value = res.data;
  });
});

const rules = reactive({
  name: [{ required: true, message: "请填写角色名" }],
  state: [{ required: true, message: "请选择状态" }],
  createUser: [{ required: true, message: "请填写创建账号" }],
  createTime: [{ required: true, message: "请填写创建时间" }]
});

const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
  //getTableList
});
const row = ref({});

// 接收父组件传过来的参数
const acceptParams = (rowData, getTableList, isView) => {
  row.value = rowData;
  drawerProps.value.getTableList = getTableList;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看系统角色";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统角色" : "新增系统角色";
  }
  drawerProps.value.visible = true;
  nextTick(() => {
    if (rowData && rowData.authList) {
      rowData.authList.forEach(ele => {
        authTree.value.setChecked(ele.id, true, false);
      });
    }
  });
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
      row.value.authList = authTree.value.getCheckedNodes(false, true);
      const api = row.value && row.value.id ? edit : add;
      await api(row.value);
      ElMessage.success({ message: `${drawerProps.value.title}成功！` });
      drawerProps.value.getTableList();
      drawerProps.value.visible = false;
    } catch (error) {
      console.log(error);
    }
  });
};

//权限树数据
const authTree = ref();
const authTreeData = ref([]);
//自定义树class
const customNodeClass = data => {
  if (data.children && data.children.length > 0) {
    let noChild = 0;
    for (let i = 0; i < data.children.length; i++) {
      if (data.children[i].children && data.children[i].children <= 0) {
        noChild++;
      }
    }
    if (noChild == data.children.length) {
      return "is-penultimate";
    }
  }
  return null;
};
const checkedAll = ref(false);
//全选/取消
const handleCheckedAllTree = () => {
  authTree.value.setCheckedNodes(checkedAll.value ? authTreeData.value : []);
};

defineExpose({
  acceptParams
});
</script>

<style scoped>
:deep(.el-tree-node.is-expanded.is-penultimate > .el-tree-node__children) {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap; /* 允许换行 */
}
/* .is-penultimate > .el-tree-node__content {
  color: #626aef;
}
.is-penultimate > .el-tree-node__children > div {
  width: 35%;
} */
</style>
