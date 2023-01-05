<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}系统权限`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="父节点" prop="parentId">
        <el-tree-select
          v-model="drawerProps.rowData!.parentId"
          :props="props"
          :data="tableData"
          :render-after-expand="false"
          show-checkbox
          check-strictly
          node-key="id"
        />
      </el-form-item>
      <el-form-item label="标题" prop="name">
        <el-input
          v-model="drawerProps.rowData!.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写标题"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="webCode">
        <el-input
          v-model="drawerProps.rowData!.webCode"
          maxlength="50"
          show-word-limit
          placeholder="请填写名称"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="权限编码" prop="code">
        <el-input
          v-model="drawerProps.rowData!.code"
          maxlength="50"
          show-word-limit
          placeholder="请填写权限编码"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="权限类型" prop="authType">
        <el-select v-model="drawerProps.rowData!.authType" placeholder="请选择权限类型" clearable>
          <el-option v-for="item in sysAuthEnum" :key="item.value" :label="item.desc" :value="item.value" />
        </el-select>
        <el-form-item label="包含权限" prop="containsCode" v-show="drawerProps.rowData!.authType=='button'">
          <el-input
            v-model="drawerProps.rowData!.containsCode"
            maxlength="255"
            show-word-limit
            placeholder="请填写包含权限"
            clearable
          ></el-input>
        </el-form-item>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="drawerProps.rowData!.sort" :min="1" placeholder="请填写排序" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="菜单图标" prop="icon">
        <SelectIcon v-model:iconValue="drawerProps.rowData!.icon" />
      </el-form-item>
      <el-form-item label="路由地址" prop="path">
        <el-input
          v-model="drawerProps.rowData!.path"
          maxlength="255"
          show-word-limit
          placeholder="请填写路由地址"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="组件路径" prop="component">
        <el-input
          v-model="drawerProps.rowData!.component"
          maxlength="255"
          show-word-limit
          placeholder="请填写组件路径"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="是否显示" prop="showFlag">
        <el-radio-group v-model="drawerProps.rowData!.showFlag">
          <el-radio :label="1">显示</el-radio>
          <el-radio :label="0">影藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否缓存" prop="cacheFlag">
        <el-radio-group v-model="drawerProps.rowData!.cacheFlag">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否外链" prop="frameFlag">
        <el-radio-group v-model="drawerProps.rowData!.frameFlag">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="外链地址" prop="frameUrl" v-show="drawerProps.rowData!.frameFlag==1">
        <el-input
          v-model="drawerProps.rowData!.frameUrl"
          maxlength="255"
          show-word-limit
          placeholder="请填写外链地址"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="drawerProps.rowData!.state">
          <el-radio label="Enable">启用</el-radio>
          <el-radio label="Disable">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="drawerProps.rowData!.remark"
          maxlength="255"
          show-word-limit
          placeholder="请填写备注"
          clearable
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit(false)">保存</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit(true)">保存并继续</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { tree } from "@/api/sys/sysAuth";
import { SysAuthEnum } from "@/api/modules/enum";
import SelectIcon from "@/components/SelectIcon/index.vue";

//权限类型枚举
const sysAuthEnum = ref<any>();
onMounted(() => {
  SysAuthEnum().then((res: any) => {
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

interface DrawerProps {
  title: string;
  isView: boolean;
  rowData?: any;
  api?: (params: any) => Promise<any>;
  getTableList?: () => Promise<any>;
}

// drawer框状态
const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  isView: false,
  title: ""
});

//父级节点数据
const tableData = ref<any>([]);
const props = {
  label: "name",
  children: "children"
};
// 接收父组件传过来的参数
const acceptParams = (params: DrawerProps): void => {
  tree({}).then((res: any) => {
    tableData.value = res.data;
  });
  if (params.title == "新增") {
    params.rowData.authType = "menu";
    params.rowData.showFlag = 1;
    params.rowData.cacheFlag = 1;
    params.rowData.frameFlag = 0;
    params.rowData.state = "Enable";
  }
  drawerProps.value = params;
  drawerVisible.value = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = (continu: boolean) => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await drawerProps.value.api!(drawerProps.value.rowData);
      ElMessage.success({ message: message });
      if (!continu) {
        drawerProps.value.getTableList!();
        drawerVisible.value = false;
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
