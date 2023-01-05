<template>
  <el-drawer v-model="drawerVisible" :destroy-on-close="true" size="50%" :title="`${r'${drawerProps.title}'}${tableRemark}`">
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="drawerProps.rowData"
      :hide-required-asterisk="drawerProps.isView"
    >
      <#list columnList as c>
      <el-form-item label="${c.label}" prop="${c.property}">
      <#if c.formType=='input' || c.formType=='phone'>
        <el-input v-model="drawerProps.rowData!.${c.property}" maxlength="${c.maxlength}" show-word-limit placeholder="请填写${c.columnRemark}" clearable></el-input>
      <#elseif c.formType=='textarea'>
        <el-input
          type="textarea"
          :rows="3"
          v-model="drawerProps.rowData!.${c.property}"
          maxlength="${c.maxlength}"
          show-word-limit
          placeholder="请填写${c.columnRemark}"
          clearable
        ></el-input>
      <#elseif c.formType=='richtext'>
        <WangEditor :disabled="drawerProps.isView" height="400px" v-model:value="drawerProps.rowData!.${c.property}" />
      <#elseif c.formType=='number'>
        <el-input-number v-model="drawerProps.rowData!.${c.property}" :min="1" placeholder="请填写${c.columnRemark}" clearable></el-input-number>
      <#elseif c.formType=='select'>
        <el-select v-model="drawerProps.rowData!.${c.property}" placeholder="请选择${c.columnRemark}" clearable>
          <el-option label="是" :value="1" />
          <el-option label="否" :value="0" />
          <el-option v-for="item in genderType" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      <#elseif c.formType=='radio'>
        <el-radio-group v-model="drawerProps.rowData!.${c.property}">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      <#elseif c.formType=='checkbox'>
        <el-checkbox-group v-model="drawerProps.rowData!.${c.property}">
          <el-checkbox label="1">label1</el-checkbox>
          <el-checkbox label="2">label2</el-checkbox>
        </el-checkbox-group>
      <#elseif c.formType=='date'>
        <el-date-picker
          v-model="drawerProps.rowData!.${c.property}"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          :disabledDate="disabledDateFun"
          placeholder="请选择日期"
        ></el-date-picker>
      <#elseif c.formType=='datetime'>
        <el-date-picker
          type="datetime"
          v-model="drawerProps.rowData!.${c.property}"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          :disabledDate="disabledDateTimeFun"
          placeholder="请选择日期时间"
        ></el-date-picker>
      <#elseif c.formType=='picture'>
        <UploadImg v-model:imageUrl="drawerProps.rowData!.${c.property}"></UploadImg>
      <#elseif c.formType=='pictures'>
        <UploadImgMultiple v-model:fileList="drawerProps.rowData!.${c.property}"></UploadImgMultiple>
      <#elseif c.formType=='file'>
        <UploadFile v-model:fileUrl="drawerProps.rowData!.${c.property}" accept=".txt"></UploadFile>
      <#elseif c.formType=='fileInput'>
        <UploadFile v-model:fileUrl="drawerProps.rowData!.${c.property}" accept=".txt"></UploadFile>
      <#elseif c.formType=='map'>
        <el-input v-model="drawerProps.rowData!.${c.property}" maxlength="${c.maxlength}" disabled placeholder="请选择地址" clearable>
          <template #append><el-button @click="amapVisible = true">选择</el-button></template>
        </el-input>
      </#if>
      </el-form-item>
      </#list>
    </el-form>
    <template #footer>
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleSubmit">确定</el-button>
    </template>
  </el-drawer>

<#if map>
  <el-dialog v-model="amapVisible" :destroy-on-close="true" width="70%" title="地图" draggable>
    <MapContainer ref="mapContainerRef"></MapContainer>
    <template #footer>
      <el-button @click="amapVisible = false">取消</el-button>
      <el-button type="primary" v-show="!drawerProps.isView" @click="handleAddress">确定</el-button>
    </template>
  </el-dialog>
</#if>
</template>

<script setup lang="ts" name="EditModelForm">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
<#if richtext>import WangEditor from "@/components/WangEditor/index.vue";</#if>
<#if picture>import UploadImg from "@/components/Upload/Img.vue";</#if>
<#if pictures>import UploadImgMultiple from "@/components/Upload/ImgMultiple.vue";</#if>
<#if fileInput>import UploadFile from "@/components/Upload/File.vue";</#if>
<#if map>import MapContainer from "@/components/amap/MapContainer.vue";</#if>

const rules = reactive({
<#list columnList as c>
  <#if c.required==1>
    <#if c.formType=='phone'>
  ${c.property}: [{ required: false, pattern: /^\d{11}$/, message: "${c.label}格式错误", trigger: "change" }],
    <#else>
  ${c.property}: [{ required: true, message: "请填写${c.label}", trigger: "change" }],
    </#if>
  <#elseif c.formType=='phone'>
  ${c.property}: [{ required: false, pattern: /^\d{11}$/, message: "${c.label}格式错误", trigger: "change" }],
  </#if>
</#list>
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

<#if date>
const disabledDateFun = (time: any) => {
  return time.getTime() < Date.now();
};
</#if>
<#if datetime>
const disabledDateTimeFun = (time: any) => {
  return time.getTime() < Date.now() - 3600 * 1000 * 24;
};
</#if>
<#if map>
//地图处理
const amapVisible = ref(false);
const mapContainerRef = ref();
const handleAddress = () => {
  const addr = mapContainerRef.value.getAddress();
  drawerProps.value.rowData.city = addr.province + addr.city;
  drawerProps.value.rowData.address = addr.addressDetail;
  drawerProps.value.rowData.lng = addr.lng;
  drawerProps.value.rowData.lat = addr.lat;
  amapVisible.value = false;
};
</#if>
defineExpose({
  acceptParams
});

</script>
