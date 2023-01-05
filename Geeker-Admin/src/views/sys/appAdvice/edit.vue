<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}app建议反馈`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="用户id" prop="userId"> </el-form-item>
      <el-form-item label="问题分类" prop="category">
        <el-input
          v-model="drawerProps.rowData!.category"
          maxlength="50"
          show-word-limit
          placeholder="请填写问题分类"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="图片" prop="picture">
        <el-input
          v-model="drawerProps.rowData!.picture"
          maxlength="200"
          show-word-limit
          placeholder="请填写图片"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="建议内容" prop="content">
        <el-input
          v-model="drawerProps.rowData!.content"
          maxlength="500"
          show-word-limit
          placeholder="请填写建议内容"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input
          v-model="drawerProps.rowData!.phone"
          maxlength="11"
          show-word-limit
          placeholder="请填写联系电话"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="create_time" prop="createTime"> </el-form-item>
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

const rules = reactive({
  category: [{ required: true, message: "请填写问题分类", trigger: "change" }],
  content: [{ required: true, message: "请填写建议内容", trigger: "change" }]
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
  drawerProps.value = params;
  drawerVisible.value = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      const { message } = await drawerProps.value.api!(drawerProps.value.rowData);
      ElMessage.success({ message: message });
      drawerProps.value.getTableList!();
      drawerVisible.value = false;
    } catch (error) {
      console.log(error);
    }
  });
};

defineExpose({
  acceptParams
});
</script>
