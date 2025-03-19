<template>
  <el-drawer
    v-model="drawerProps.visible"
    class="plus-drawer-form"
    :destroy-on-close="true"
    size="40%"
    :title="drawerProps.title"
  >
    <el-form
      ref="ruleFormRef"
      label-width="120px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="权限类型" prop="authType" class="plus-form-item">
        <el-radio-group v-model="row.authType" @change="loadTreeData">
          <el-radio-button
            v-for="item in sysAuthEnum"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        /></el-radio-group>
      </el-form-item>

      <el-form-item label="父节点" prop="parentId" class="plus-form-item">
        <el-tree-select
          v-model="row.parentId"
          :props="props"
          :data="tableData"
          :render-after-expand="false"
          show-checkbox
          check-strictly
          node-key="id"
        />
      </el-form-item>
      <el-form-item label="标题" prop="name" class="plus-form-item">
        <el-input
          v-model="row.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写标题"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        :label="
          row.authType == 'Menu' || row.authType == 'Dir'
            ? '路由名称'
            : '前端权限编码'
        "
        prop="webCode"
        class="plus-form-item"
      >
        <el-input
          v-model="row.webCode"
          maxlength="50"
          show-word-limit
          placeholder="请填写名称"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="后端权限编码" prop="code" class="plus-form-item">
        <el-input
          v-model="row.code"
          maxlength="50"
          show-word-limit
          placeholder="请填写后端权限"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu' || row.authType == 'Dir'"
        :rules="{
          required: row.authType == 'Menu' || row.authType == 'Dir',
          message: '请填写路由地址'
        }"
        label="路由地址"
        prop="path"
        class="plus-form-item"
      >
        <el-input
          v-model="row.path"
          maxlength="255"
          show-word-limit
          placeholder="请填写路由地址"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu'"
        :rules="{
          required: row.authType == 'Menu',
          message: '请填写组件路径'
        }"
        label="组件路径"
        prop="component"
        class="plus-form-item"
      >
        <el-input
          v-model="row.component"
          maxlength="255"
          show-word-limit
          placeholder="请填写组件路径"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu' || row.authType == 'Dir'"
        label="菜单图标"
        prop="icon"
        class="plus-form-item"
      >
        <IconSelect v-model="row.icon" class="w-full" />
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu'"
        label="是否外链"
        prop="frameFlag"
        class="plus-form-item"
      >
        <el-radio-group v-model="row.frameFlag" class="plus-form-item-field">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu' && row.frameFlag == 1"
        label="外链地址"
        prop="frameUrl"
        class="plus-form-item"
      >
        <el-input
          v-model="row.frameUrl"
          maxlength="255"
          show-word-limit
          placeholder="请填写外链地址"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu'"
        label="是否缓存"
        prop="cacheFlag"
        class="plus-form-item"
      >
        <el-radio-group v-model="row.cacheFlag" class="plus-form-item-field">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        v-show="row.authType == 'Menu' || row.authType == 'Dir'"
        label="是否显示"
        prop="showFlag"
        class="plus-form-item"
      >
        <el-radio-group v-model="row.showFlag" class="plus-form-item-field">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="state" class="plus-form-item">
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
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import EpQuestionFilled from "@iconify-icons/ep/question-filled";
import { IconSelect } from "@/components/ReIcon";

import { enums } from "@/api/sys/common";
import { add, edit, tree } from "./sysAuth";

//权限类型枚举
const sysAuthEnum = ref([]);
const stateEnums = ref([]);
onMounted(() => {
  enums("SysAuthEnum").then(res => {
    sysAuthEnum.value = res.data;
  });
  enums("EnableState").then(res => {
    stateEnums.value = res.data;
  });
});

const rules = reactive({
  name: [{ required: true, message: "请填写名称" }],
  webCode: [{ required: true, message: "请填写前端权限编码" }],
  authType: [{ required: true, message: "请填写权限类型" }],
  state: [{ required: true, message: "请填写状态" }]
});

const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
  //getTableList
});
const row = ref({});

//父级节点数据
const tableData = ref([]);
const props = {
  label: "name",
  children: "children"
};

// 接收父组件传过来的参数
const acceptParams = (rowData, getTableList, isView) => {
  tree({}).then(res => {
    tableData.value = res.data;
  });
  row.value = rowData;
  drawerProps.value.getTableList = getTableList;
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
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
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

defineExpose({
  acceptParams
});
</script>
