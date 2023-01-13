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
      <el-form-item label="文章类型" prop="type">
        <el-select v-model="drawerProps.rowData!.type" placeholder="请选择" clearable>
          <el-option v-for="item in appArticleEnums" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="drawerProps.rowData!.title"
          maxlength="255"
          show-word-limit
          placeholder="请填写标题"
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
      <el-form-item label="图片" prop="picture" v-show="drawerProps.rowData!.type!='system'">
        <UploadImg v-model:imageUrl="drawerProps.rowData!.picture"></UploadImg>
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
      <el-form-item label="图文详情" prop="content" v-show="drawerProps.rowData!.contentType=='content'">
        <WangEditor :disabled="drawerProps.isView" v-model:value="drawerProps.rowData!.content" />
      </el-form-item>
      <!-- <el-form-item label="文章位置" prop="location">
        <el-input
          v-model="drawerProps.rowData!.location"
          maxlength="255"
          show-word-limit
          placeholder="请填写"
          clearable
        ></el-input>
      </el-form-item> -->
    </el-form>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive, onMounted, computed } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import WangEditor from "@/components/WangEditor/index.vue";
import UploadImg from "@/components/Upload/Img.vue";

import { detail } from "@/api/sys/appArticle";
import { AppArticleType } from "@/api/modules/enum";

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

const clinkUrlRequired = computed(() => {
  if (drawerProps.value.rowData && drawerProps.value.rowData.contentType == "link") {
    return true;
  }
  return false;
});
const contentRequired = computed(() => {
  if (drawerProps.value.rowData && drawerProps.value.rowData.contentType == "content") {
    return true;
  }
  return false;
});

const rules = reactive({
  type: [{ required: true, message: "请填写文章类型", trigger: "change" }],
  title: [{ required: true, message: "请填写标题", trigger: "change" }],
  contentType: [{ required: true, message: "请填写内容类型", trigger: "change" }],
  content: [{ required: contentRequired, message: "请填写图文详情", trigger: "change" }],
  linkUrl: [{ required: clinkUrlRequired, message: "请填写超链接", trigger: "change" }]
});

// 接收父组件传过来的参数
const acceptParams = (params: DrawerProps): void => {
  drawerProps.value = params;
  drawerVisible.value = true;
  if (params.rowData.id) {
    detail(params.rowData.id).then(res => {
      drawerProps.value.rowData = res.data;
    });
  } else {
    drawerProps.value.rowData!.contentType = "content";
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

const appArticleEnums = ref<any[]>([]);
onMounted(() => {
  AppArticleType().then(res => {
    appArticleEnums.value = res.data;
  });
});

defineExpose({
  acceptParams
});
</script>
