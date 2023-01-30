<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}app轮播图`">
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
        <el-input v-model="drawerProps.rowData!.title" maxlength="200" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
      <el-form-item label="图片" prop="picture">
        <UploadImg v-model:imageUrl="drawerProps.rowData!.picture"></UploadImg>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="drawerProps.rowData!.type" placeholder="请选择" clearable>
          <el-option v-for="item in bannerEnums" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="链接地址" prop="linkUrl" v-show="drawerProps.rowData!.type=='url_link'">
        <el-input
          v-model="drawerProps.rowData!.linkUrl"
          maxlength="255"
          show-word-limit
          placeholder="请填写"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="图片详情" prop="content" v-show="drawerProps.rowData!.type=='content'">
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
import { ref, reactive, onMounted, computed } from "vue";
import { ElMessage, FormInstance } from "element-plus";

import UploadImg from "@/components/Upload/Img.vue";
import WangEditor from "@/components/WangEditor/index.vue";
import { AppBannerEnum } from "@/api/modules/enum";
import { detail } from "@/api/sys/appBanner";

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

const linkUrlRequired = computed(() => {
  if (drawerProps.value.rowData && drawerProps.value.rowData.type == "url_link") {
    return true;
  }
  return false;
});
const contentRequired = computed(() => {
  if (drawerProps.value.rowData && drawerProps.value.rowData.type == "content") {
    return true;
  }
  return false;
});
const rules = reactive({
  title: [{ required: true, message: "请填写标题", trigger: "change" }],
  picture: [{ required: true, message: "请填写图片", trigger: "change" }],
  type: [{ required: true, message: "请填写类型", trigger: "change" }],
  content: [{ required: contentRequired, message: "请填写图文详情", trigger: "change" }],
  linkUrl: [{ required: linkUrlRequired, message: "请填写链接地址", trigger: "change" }]
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
    drawerProps.value.rowData.type = "no_link";
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

const bannerEnums = ref<any[]>([]);
onMounted(() => {
  AppBannerEnum().then(res => {
    bannerEnums.value = res.data;
  });
});

defineExpose({
  acceptParams
});
</script>
