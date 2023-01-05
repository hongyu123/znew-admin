<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}系统角色`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="角色名" prop="name">
        <el-input
          v-model="drawerProps.rowData!.name"
          maxlength="50"
          show-word-limit
          placeholder="请填写角色名"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="角色编码" prop="code">
        <el-input
          v-model="drawerProps.rowData!.code"
          maxlength="50"
          show-word-limit
          placeholder="请填写角色编码"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="drawerProps.rowData!.sort" :min="1" placeholder="请填写排序" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="drawerProps.rowData!.state">
          <el-radio label="Enable">启用</el-radio>
          <el-radio label="Disable">禁用</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="权限">
        <el-tree
          node-key="id"
          ref="authTree"
          :props="{ label: 'name', children: 'children', class: customNodeClass }"
          :data="authTreeData"
          show-checkbox
          default-expand-all
        />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="drawerProps.rowData!.checkedAll" @change="handleCheckedAllTree">全选</el-checkbox>
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
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { tree } from "@/api/sys/sysAuth";
import { detail } from "@/api/sys/sysRole";

const rules = reactive({
  name: [{ required: true, message: "请填写角色名", trigger: "change" }],
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

// 接收父组件传过来的参数
const acceptParams = (params: DrawerProps): void => {
  if (params.title == "新增") {
    params.rowData.state = "Enable";
  }
  drawerProps.value = params;
  drawerVisible.value = true;
  if (authTree.value) {
    authTree.value.setCheckedNodes([]);
  }
  if (params.rowData.id) {
    detail(params.rowData.id).then(res => {
      drawerProps.value.rowData = res.data;
      //选中节点
      res.data.authList.forEach((ele: any) => {
        authTree.value.setChecked(ele.id, true, false);
      });
    });
  }
};

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      drawerProps.value.rowData.authList = authTree.value.getCheckedNodes(false, true);
      const { message } = await drawerProps.value.api!(drawerProps.value.rowData);
      ElMessage.success({ message: message });
      drawerProps.value.getTableList!();
      drawerVisible.value = false;
    } catch (error) {
      console.log(error);
    }
  });
};

//权限树数据
const authTree = ref();
const authTreeData = ref<any>([]);
tree({}).then((res: any) => {
  authTreeData.value = res.data;
});
//自定义树class
const customNodeClass = (data: any) => {
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
  authTree.value.setCheckedNodes(drawerProps.value.rowData!.checkedAll ? authTreeData.value : []);
};
defineExpose({
  acceptParams
});
</script>
<style>
/* .is-penultimate > .el-tree-node__content {
  color: #626aef;
} */
.el-tree-node.is-expanded.is-penultimate > .el-tree-node__children {
  display: flex;
  flex-direction: row;
}
.is-penultimate > .el-tree-node__children > div {
  width: 35%;
}
</style>
