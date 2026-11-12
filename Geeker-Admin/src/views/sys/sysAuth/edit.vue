<template>
  <el-drawer v-model="drawerProps.visible" :destroy-on-close="true" size="30%" :title="drawerProps.title">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="父节点" prop="parentId">
        <el-tree-select
          v-model="row.parentId"
          :props="treeProps"
          :data="tableData"
          :render-after-expand="false"
          show-checkbox
          check-strictly
          node-key="id"
        />
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="row.title" maxlength="50" show-word-limit clearable></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="row.name" maxlength="50" show-word-limit clearable></el-input>
      </el-form-item>
      <el-form-item label="权限编码" prop="code">
        <el-input v-model.trim="row.code" maxlength="50" show-word-limit clearable></el-input>
      </el-form-item>
      <el-form-item label="权限类型" prop="authType">
        <el-select v-model="row.authType" clearable>
          <el-option v-for="item in sysAuthEnum" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="row.sort" :min="1" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="菜单图标" prop="icon">
        <SelectIcon v-model:iconValue="row.icon" />
      </el-form-item>
      <el-form-item label="路由地址" prop="path">
        <el-input v-model="row.path" maxlength="255" show-word-limit clearable></el-input>
      </el-form-item>
      <el-form-item label="组件路径" prop="component">
        <el-input v-model="row.component" maxlength="255" show-word-limit clearable></el-input>
      </el-form-item>
      <el-form-item label="是否显示" prop="showFlag">
        <el-radio-group v-model="row.showFlag">
          <el-radio :value="1">显示</el-radio>
          <el-radio :value="0">影藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否缓存" prop="cacheFlag">
        <el-radio-group v-model="row.cacheFlag">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否外链" prop="frameFlag">
        <el-radio-group v-model="row.frameFlag">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="外链地址" prop="frameUrl" v-show="row.frameFlag == 1">
        <el-input v-model="row.frameUrl" maxlength="255" show-word-limit clearable></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="row.state">
          <el-radio value="Enable">启用</el-radio>
          <el-radio value="Disable">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="row.remark" maxlength="255" show-word-limit clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit(false)">保存</el-button>
      <el-button type="primary" v-show="!drawerProps.isView && !row.id" @click="handleSubmit(true)">保存并继续</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="js" name="SysAuthDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { enums } from "@/api/modules/common";
import { add, edit, tree } from "./api";
import SelectIcon from "@/components/SelectIcon/index.vue";

//权限类型枚举
const sysAuthEnum = ref();
onMounted(() => {
  enums("SysAuthEnum").then(res => {
    sysAuthEnum.value = res.data;
  });
});

const rules = reactive({
  name: [{ required: true, message: "请填写标题", trigger: "change" }],
  webCode: [{ required: true, message: "请填写名称", trigger: "change" }],
  authType: [{ required: true, message: "请填写权限类型(目录,菜单,安全,权限)", trigger: "change" }],
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

//父级节点数据
const tableData = ref([]);
const treeProps = {
  label: "title",
  children: "children"
};
// 接收父组件传过来的参数
const acceptParams = (rowData, refreshTable, isView) => {
  tree("").then(res => {
    tableData.value = res.data;
  });

  row.value = rowData;
  drawerProps.value.refreshTable = refreshTable;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看系统权限";
  } else {
    drawerProps.value.title = rowData.id ? "编辑系统权限" : "新增系统权限";
  }
  drawerProps.value.visible = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = continu => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
      const api = row.value && row.value.id ? edit : add;
      const { message } = await api(row.value);
      ElMessage.success({ message: message });
      if (!continu) {
        drawerProps.value.refreshTable();
        drawerProps.value.visible = false;
      }
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({
  acceptParams
});
</script>
