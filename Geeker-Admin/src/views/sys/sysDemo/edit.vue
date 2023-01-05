<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${drawerProps.title}系统示例表`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-form-item label="名称" prop="name">
        <el-input v-model="drawerProps.rowData!.name" maxlength="50" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input-number v-model="drawerProps.rowData!.age" :min="1" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="分数" prop="score">
        <el-input-number v-model="drawerProps.rowData!.score" :min="1" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="drawerProps.rowData!.gender" placeholder="请选择" clearable>
          <el-option v-for="item in genderEnums" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="启用状态" prop="state">
        <el-radio-group v-model="drawerProps.rowData!.state">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="兴趣" prop="interestList">
        <el-checkbox-group v-model="drawerProps.rowData!.interestList">
          <el-checkbox label="1">篮球</el-checkbox>
          <el-checkbox label="2">足球</el-checkbox>
        </el-checkbox-group>
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
      <el-form-item label="注册时间" prop="registTime">
        <el-date-picker
          type="datetime"
          v-model="drawerProps.rowData!.registTime"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          :disabledDate="disabledDateTimeFun"
          placeholder="请选择日期时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <UploadImg v-model:imageUrl="drawerProps.rowData!.avatar"> </UploadImg>
      </el-form-item>
      <el-form-item label="照片" prop="pictureList">
        <UploadImgMultiple v-model:fileList="drawerProps.rowData!.pictureList"> </UploadImgMultiple>
      </el-form-item>
      <el-form-item label="附件" prop="attachment">
        <UploadFile v-model:fileUrl="drawerProps.rowData!.attachment" accept=".txt"></UploadFile>
      </el-form-item>
      <el-form-item label="文件输入" prop="fileInput">
        <FileInput v-model:fileUrl="drawerProps.rowData!.fileInput" accept=".txt"></FileInput>
      </el-form-item>
      <el-form-item label="简介" prop="introduction">
        <el-input
          type="textarea"
          :rows="3"
          v-model="drawerProps.rowData!.introduction"
          maxlength="255"
          show-word-limit
          placeholder="请填写"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="详情" prop="detail">
        <WangEditor :disabled="drawerProps.isView" height="400px" v-model:value="drawerProps.rowData!.detail" />
      </el-form-item>
      <el-form-item label="手机" prop="phone">
        <el-input v-model="drawerProps.rowData!.phone" maxlength="11" show-word-limit placeholder="请填写" clearable></el-input>
      </el-form-item>
      <el-form-item label="纬度" prop="lat">
        <el-input-number v-model="drawerProps.rowData!.lat" :min="1" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="经度" prop="lng">
        <el-input-number v-model="drawerProps.rowData!.lng" :min="1" placeholder="请填写" clearable></el-input-number>
      </el-form-item>
      <el-form-item label="地址" prop="location">
        <el-input v-model="drawerProps.rowData!.location" maxlength="200" disabled placeholder="请选择地址" clearable>
          <template #append><el-button @click="amapVisible = true">选择</el-button></template>
        </el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>

  <el-dialog v-model="amapVisible" :destroy-on-close="true" width="70%" title="地图" draggable>
    <MapContainer ref="mapContainerRef"></MapContainer>
    <template #footer>
      <el-button @click="amapVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleAddress">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import WangEditor from "@/components/WangEditor/index.vue";
import UploadImg from "@/components/Upload/Img.vue";
import UploadImgMultiple from "@/components/Upload/ImgMultiple.vue";
import MapContainer from "@/components/amap/MapContainer.vue";
import UploadFile from "@/components/Upload/File.vue";
import FileInput from "@/components/Upload/FileInput.vue";

import { GenderEnum } from "@/api/modules/enum";
import { detail } from "@/api/sys/sysDemo";

const rules = reactive({
  name: [{ required: true, message: "请填写", trigger: "change" }],
  registTime: [{ required: true, message: "请填写", trigger: "change" }],
  avatar: [{ required: true, message: "请填写", trigger: "change" }],
  introduction: [{ required: true, message: "请填写", trigger: "change" }],
  phone: [{ required: true, message: "请填写", trigger: "change" }],
  lat: [{ required: true, message: "请填写", trigger: "change" }],
  lng: [{ required: true, message: "请填写", trigger: "change" }]
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
  if (params.rowData.id) {
    detail(params.rowData.id).then(res => {
      params.rowData = res.data;
      drawerProps.value = params;
      drawerVisible.value = true;
    });
  } else {
    drawerProps.value = params;
    drawerVisible.value = true;
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

const disabledDateFun = (time: any) => {
  return time.getTime() < Date.now();
};
const disabledDateTimeFun = (time: any) => {
  return time.getTime() < Date.now() - 3600 * 1000 * 24;
};
//地图处理
const amapVisible = ref(false);
const mapContainerRef = ref();
const handleAddress = () => {
  const addr = mapContainerRef.value.getAddress();
  drawerProps.value.rowData.city = addr.province + addr.city;
  drawerProps.value.rowData.location = addr.addressDetail;
  drawerProps.value.rowData.lng = addr.lng;
  drawerProps.value.rowData.lat = addr.lat;
  amapVisible.value = false;
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
