<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="500px" :title="`${drawerProps.title}app用户`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model="drawerProps.rowData!.nickname"
          maxlength="100"
          show-word-limit
          placeholder="请填写昵称"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="photo">
        <el-avatar shape="square" size="100" fit="contain" :src="drawerProps.rowData!.photo"></el-avatar>
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="drawerProps.rowData!.phone"
          maxlength="11"
          show-word-limit
          placeholder="请填写手机号码"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-input v-model="drawerProps.rowData!.genderDesc" :min="1" placeholder="请填写性别" clearable></el-input>
      </el-form-item>
      <el-form-item label="生日" prop="birth">
        <el-input v-model="drawerProps.rowData!.birth" placeholder="请填写生日" clearable></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="drawerProps.rowData!.address"
          maxlength="50"
          show-word-limit
          placeholder="请填写地址"
          clearable
        ></el-input>
      </el-form-item>

      <el-form-item label="状态" prop="state" v-show="drawerProps.isView">
        <el-tag v-if="drawerProps.rowData!.state=='Enable'">启用</el-tag>
        <el-tag v-else type="info">禁用</el-tag>
      </el-form-item>

      <h4>活动数据信息</h4>
      <el-form-item label="关注数量" prop="focusNum">
        <el-input v-model="drawerProps.rowData!.focusNum" :min="1" placeholder="请填写关注数量" clearable></el-input>
      </el-form-item>
      <el-form-item label="粉丝数量" prop="fansNum">
        <el-input v-model="drawerProps.rowData!.fansNum" :min="1" placeholder="请填写粉丝数量" clearable></el-input>
      </el-form-item>
      <el-form-item label="积分" prop="integral">
        <el-input v-model="drawerProps.rowData!.integral" :min="1" placeholder="请填写积分" clearable></el-input>
      </el-form-item>
      <el-form-item label="作品数量" prop="worksNum">
        <el-input v-model="drawerProps.rowData!.worksNum" :min="1" placeholder="请填写作品数量" clearable></el-input>
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

const rules = reactive({});

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
<style scoped>
/**input 禁用时不显示placehoher*/
::v-deep input:disabled::-webkit-input-placeholder {
  -webkit-text-fill-color: rgba(255, 255, 255, 0);
}
</style>
