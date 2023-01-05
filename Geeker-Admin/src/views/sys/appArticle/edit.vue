<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="70%" :title="`${drawerProps.title}app文章`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="drawerProps.rowData!.title"
          maxlength="255"
          show-word-limit
          placeholder="请填写标题"
          :disabled="drawerProps.rowData!.type=='system'"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="introduction">
        <el-input
          v-model="drawerProps.rowData!.introduction"
          maxlength="255"
          show-word-limit
          placeholder="请填写简介"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="内容类型" prop="contentType" v-show="drawerProps.rowData!.type!='system'">
        <el-radio-group v-model="drawerProps.rowData!.contentType">
          <el-radio label="content">图文内容</el-radio>
          <el-radio label="link">超链接</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="超链接" prop="linkUrl" v-show="drawerProps.rowData!.contentType=='link'">
        <el-input
          v-model="drawerProps.rowData!.linkUrl"
          maxlength="255"
          show-word-limit
          placeholder="请填写超链接"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="图文内容" prop="content" v-show="drawerProps.rowData!.contentType=='content'">
        <WangEditor :disabled="drawerProps.isView" height="400px" v-model:value="drawerProps.rowData!.content" />
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
import WangEditor from "@/components/WangEditor/index.vue";
import { detail } from "@/api/sys/appArticle";

const rules = reactive({
  title: [{ required: true, message: "请填写标题", trigger: "change" }],
  contentType: [{ required: true, message: "请填写内容类型(图文/超链接)", trigger: "change" }]
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
  if (params.rowData.id) {
    detail(params.rowData.id).then(res => {
      drawerProps.value.rowData = res.data;
    });
  }
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
