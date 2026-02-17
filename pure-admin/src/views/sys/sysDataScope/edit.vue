<template>
  <el-drawer
    v-model="drawerProps.visible"
    class="plus-drawer-form"
    :destroy-on-close="true"
    size="30%"
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
      <el-form-item label="配置类型" prop="configType">
        <el-radio-group v-model="row.configType">
          <el-radio :value="1">用户</el-radio>
          <el-radio :value="2">组织结构</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        v-if="row.configType == 1"
        label="用户名称"
        prop="configId"
        class="plus-form-item"
      >
        <el-input
          v-model="row.configName"
          maxlength="100"
          show-word-limit
          placeholder="请选择用户"
          clearable
          disabled
          class="plus-form-item-field"
        >
          <template #append>
            <el-button type="primary" @click="selectDataVisible = true">
              选择
            </el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item
        v-else
        label="组织机构"
        prop="configId"
        class="plus-form-item"
      >
        <el-tree-select
          v-model="row.configId"
          :props="{ label: 'name', children: 'children' }"
          :data="treeSelectData"
          :render-after-expand="false"
          check-strictly
          node-key="id"
          @current-change="handleOrgChange"
        />
      </el-form-item>

      <el-form-item label="数据key" prop="dataKey" class="plus-form-item">
        <el-input
          v-model="row.dataKey"
          maxlength="100"
          show-word-limit
          placeholder="请填写数据key"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
      <el-form-item label="数据权限" prop="dataScope" class="plus-form-item">
        <el-select
          v-model="row.dataScope"
          clearable
          placeholder="请选择数据权限"
          class="plus-form-item-field"
        >
          <el-option
            v-for="item in dataScopeEnums"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        v-show="row.dataScope == 'Custom'"
        label="自定义部门"
        prop="customIds"
        class="plus-form-item"
      >
        <el-tree-select
          v-model="row.customIds"
          :props="{ label: 'name', children: 'children' }"
          :data="treeSelectData"
          :render-after-expand="false"
          check-strictly
          node-key="id"
          multiple
          show-checkbox
        />
      </el-form-item>
      <el-dialog
        v-model="selectDataVisible"
        :destroy-on-close="true"
        width="50%"
        title="选择用户"
        draggable
      >
        <SysUserSelect ref="selectDataRef" />
        <template #footer>
          <el-button @click="selectDataVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSelectData">确定</el-button>
        </template>
      </el-dialog>
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
import SysUserSelect from "../sysUser/select.vue";

import { enums } from "@/api/sys/common";
import { add, edit } from "./sysDataScope";
import { tree } from "../sysOrganization/sysOrganization";
const dataScopeEnums = ref([]);
const treeSelectData = ref([]);
onMounted(() => {
  enums("DataScope").then(res => {
    dataScopeEnums.value = res.data;
  });
  tree("Enable").then(res => {
    treeSelectData.value = res.data;
  });
});

const rules = reactive({
  configType: [{ required: true, message: "请选择配置类型" }],
  configId: [{ required: true, message: "请填写用户/机构id" }],
  dataScope: [{ required: true, message: "请选择数据权限" }]
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
    drawerProps.value.title = "查看数据权限配置";
  } else {
    drawerProps.value.title = rowData.id
      ? "编辑数据权限配置"
      : "新增数据权限配置";
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

const handleOrgChange = data => {
  row.value.configName = data.name;
};

const selectDataVisible = ref(false);
const selectDataRef = ref();
const handleSelectData = async () => {
  const selectData = selectDataRef.value.getSelected();
  if (selectData.length <= 0) {
    ElMessage.error({ message: "请选择用户" });
    return false;
  }
  row.value.configId = selectData[0].id;
  row.value.configName = selectData[0].nickname || selectData[0].account;
  selectDataVisible.value = false;
};

defineExpose({
  acceptParams
});
</script>
