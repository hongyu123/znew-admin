<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}系统用户`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="账号" prop="account" v-show="!drawerProps.rowData!.id">
        <el-input
          v-model="drawerProps.rowData!.account"
          maxlength="50"
          show-word-limit
          placeholder="请填写账号"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" v-show="!drawerProps.rowData!.id">
        <el-input v-model="drawerProps.rowData!.password" type="password" placeholder="请填写密码" clearable></el-input>
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
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model="drawerProps.rowData!.nickname"
          maxlength="50"
          show-word-limit
          placeholder="请填写昵称"
          clearable
        ></el-input>
      </el-form-item>
      <!-- <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:imageUrl="drawerProps.rowData!.avatar" :api="upload" width="250px">
          <template #empty>
            <el-icon><Picture /></el-icon>
            <span>请上传图片</span>
          </template>
        </UploadImg>
      </el-form-item> -->
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="drawerProps.rowData!.email"
          maxlength="50"
          show-word-limit
          placeholder="请填写邮箱"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="drawerProps.rowData!.gender">
          <el-radio label="Male">男</el-radio>
          <el-radio label="Female">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="drawerProps.rowData!.state">
          <el-radio label="Enable">启用</el-radio>
          <el-radio label="Disable">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="角色" prop="roleList">
        <el-select multiple v-model="drawerProps.rowData!.roleList" placeholder="请选择角色" clearable>
          <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
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
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
//import { upload } from "@/api/modules/common";
//import UploadImg from "@/components/Upload/Img.vue";
import { list as reqRoleList } from "@/api/sys/sysRole";
import { detail } from "@/api/sys/sysUser";

//角色列表
const roleList = ref<any>([]);
onMounted(() => {
  reqRoleList({ state: "Enable" }).then((res: any) => {
    roleList.value = res.data;
  });
});

const rules = reactive({
  account: [{ required: true, message: "请填写账号", trigger: "change" }],
  password: [{ required: true, pattern: /^\w{6,16}$/, message: "密码格式错误", trigger: "change" }],
  phone: [{ required: false, pattern: /^\d{11}$/, message: "手机号码格式错误", trigger: "change" }],
  email: [{ pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: "邮箱格式错误", trigger: "change" }],
  state: [{ required: true, message: "请填写状态", trigger: "change" }],
  roleList: [{ required: true, message: "请填写角色", trigger: "change" }]
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
    rules.account[0].required = false;
    rules.password[0].required = false;
    detail(params.rowData.id).then(res => {
      drawerProps.value.rowData = res.data;
    });
  } else {
    drawerProps.value.rowData.state = "Enable";
    rules.account[0].required = true;
    rules.password[0].required = true;
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
