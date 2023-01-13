<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}app用户`">
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
          placeholder="请填写"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:imageUrl="drawerProps.rowData!.avatar"></UploadImg>
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="drawerProps.rowData!.phone" maxlength="11" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="drawerProps.rowData!.gender" placeholder="请选择" clearable>
          <el-option v-for="item in genderEnums" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="生日" prop="birth">
        <el-date-picker
          v-model="drawerProps.rowData!.birth"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          :disabledDate="disabledDateFun"
          placeholder="请选择日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="drawerProps.rowData!.address" maxlength="50" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
      <el-form-item label="积分" prop="integral">
        <el-input-number v-model="drawerProps.rowData!.integral" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="余额" prop="balance">
        <el-input-number v-model="drawerProps.rowData!.balance" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <!-- <el-form-item label="角色" prop="role">
        <el-select v-model="drawerProps.rowData!.role" placeholder="请选择" clearable>
          <el-option v-for="item in genderEnums" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="启用状态" prop="enableState">
        <el-select v-model="drawerProps.rowData!.enableState" placeholder="请选择" clearable>
          <el-option label="启用" value="Enable" />
          <el-option label="禁用" value="Disable" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="comment">
        <el-input
          v-model="drawerProps.rowData!.comment"
          maxlength="255"
          show-word-limit
          placeholder="请填写"
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
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";

import UploadImg from "@/components/Upload/Img.vue";
import { GenderEnum } from "@/api/modules/enum";

const rules = reactive({
  nickname: [{ required: true, message: "请填写昵称", trigger: "change" }],
  phone: [{ required: false, pattern: /^\d{11}$/, message: "手机号码格式错误", trigger: "change" }],
  enableState: [{ required: true, message: "请填写启用状态", trigger: "change" }]
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

const disabledDateFun = (time: any) => {
  return time.getTime() < Date.now();
};

const genderEnums = ref<any[]>([]);
onMounted(() => {
  GenderEnum().then(res => {
    genderEnums.value = res.data;
  });
});

defineExpose({
  acceptParams
});
</script>
