<template>
  <el-drawer v-model="drawerProps.visible" :destroy-on-close="true" size="30%" :title="drawerProps.title" @opened="onopened">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="角色名" prop="name">
        <el-input v-model="row.name" maxlength="50" show-word-limit placeholder="请填写角色名" clearable></el-input>
      </el-form-item>
      <el-form-item label="角色编码" prop="code">
        <el-input v-model="row.code" maxlength="50" show-word-limit placeholder="请填写角色编码" clearable></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="row.sort" :min="1" placeholder="请填写排序" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio value="Enable">启用</el-radio>
          <el-radio value="Disable">禁用</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="权限">
        <el-tree
          node-key="id"
          ref="authTree"
          :props="{ label: 'title', children: 'children', class: customNodeClass }"
          :data="authTreeData"
          show-checkbox
        />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="row.checkedAll" @change="handleCheckedAllTree">全选</el-checkbox>
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input v-model="row.remark" maxlength="255" show-word-limit placeholder="请填写备注" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="js" name="SysRoleDrawer">
import { ref, reactive } from "vue";
import { ElMessage } from "element-plus";
import { tree } from "../sysAuth/api";
import { add, edit } from "./api";

const rules = reactive({
  name: [{ required: true, message: "请填写角色名", trigger: "change" }],
  state: [{ required: true, message: "请填写状态", trigger: "change" }],
  creator: [{ required: true, message: "请填写创建账号", trigger: "change" }],
  createTime: [{ required: true, message: "请填写创建时间", trigger: "change" }]
});

// drawer框状态
const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
});
const row = ref({});

const onopened = () => {
  if (row.value.authList && row.value.authList.length > 0) {
    row.value.authList.forEach(ele => {
      authTree.value.setChecked(ele.id, true, false);
    });
  } else {
    authTree.value.setCheckedNodes([]);
  }
};

// 接收父组件传过来的参数
const acceptParams = (rowData, refreshTable, isView) => {
  row.value = rowData;
  drawerProps.value.refreshTable = refreshTable;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看系统角色";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统角色" : "新增系统角色";
  }
  drawerProps.value.visible = true;

  tree().then(res => {
    authTreeData.value = res.data;
  });
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    row.value.authList = authTree.value.getCheckedNodes(false, true);
    const api = row.value && row.value.id ? edit : add;
    try {
      const { message } = await api(row.value);
      ElMessage.success({ message: message });
      drawerProps.value.refreshTable();
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
//全选/取消
const handleCheckedAllTree = () => {
  authTree.value.setCheckedNodes(row.value.checkedAll ? authTreeData.value : []);
};
defineExpose({
  acceptParams
});
</script>
