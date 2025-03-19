<template>
  <el-drawer
    v-model="drawerProps.visible"
    class="plus-drawer-form"
    :destroy-on-close="true"
    size="30%"
    :title="drawerProps.title"
  >
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="row"
      :hide-required-asterisk="drawerProps.isView"
    >
    <#list columnList as c>
    <#if c.formType=='input' || c.formType=='phone'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-input
          v-model="row.${c.property}"
          maxlength="${c.maxlength}"
          show-word-limit
          placeholder="请填写${c.label}"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
    <#elseif c.formType=='textarea'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-input
          v-model="row.${c.property}"
          type="textarea"
          :rows="3"
          maxlength="${c.maxlength}"
          show-word-limit
          placeholder="请填写${c.label}"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
    <#elseif c.formType=='richtext'>
      <el-form-item label="${c.label}" prop="${c.property}">
        <WangEditor
          v-model:value="row.${c.property}"
          :disabled="drawerProps.isView"
          height="400px"
        />
      </el-form-item>
    <#elseif c.formType=='number'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-input-number
          v-model="row.${c.property}"
          :min="0"
          clearable
          class="plus-form-item-field"
        />
      </el-form-item>
    <#elseif c.formType=='select'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-select
          v-model="row.${c.property}"
          clearable
          placeholder="请选择${c.label}"
          class="plus-form-item-field"
        >
          <el-option label="是" :value="1" />
          <el-option label="否" :value="0" />
        </el-select>
      </el-form-item>
    <#elseif c.formType=='enum'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-select
          v-model="row.${c.property}"
          clearable
          placeholder="请选择${c.label}"
          class="plus-form-item-field"
        >
          <el-option
            v-for="item in ${c.property}Enums"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    <#elseif c.formType=='radio'>
      <el-form-item label="${c.label}" prop="${c.property}">
        <el-radio-group v-model="row.${c.property}">
          <el-radio :value="1">是</el-radio>
          <el-radio :value="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
    <#elseif c.formType=='checkbox'>
      <el-form-item label="${c.label}" prop="${c.property}">
        <el-checkbox-group v-model="row.${c.property}">
          <el-checkbox value="1" label="篮球" />
          <el-checkbox value="2" label="足球" />
        </el-checkbox-group>
      </el-form-item>
    <#elseif c.formType=='date'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-date-picker
          v-model="row.${c.property}"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          placeholder="请选择${c.label}"
          class="plus-form-item-field"
          :disabledDate="disabledDateFun"
        />
      </el-form-item>
    <#elseif c.formType=='datetime'>
      <el-form-item label="${c.label}" prop="${c.property}" class="plus-form-item">
        <el-date-picker
          v-model="row.${c.property}"
          type="datetime"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择${c.label}"
          class="plus-form-item-field"
          :disabledDate="disabledDateTimeFun"
        />
      </el-form-item>
    <#elseif c.formType=='picture'>
      <el-form-item label="${c.label}" prop="${c.property}">
        <UploadImg v-model:imageUrl="row.${c.property}" />
      </el-form-item>
    <#elseif c.formType=='pictures'>
      <el-form-item label="${c.label}" prop="${c.property}">
        <UploadImgMul v-model:fileList="row.${c.property}" />
      </el-form-item>
    <#elseif c.formType=='file'>
      <el-form-item label="${c.label}" prop="${c.property}">
        <UploadFiles v-model:fileList="row.${c.property}" />
      </el-form-item>
    </#if>
    </#list>
    </el-form>
    <template #footer>
      <el-button @click="drawerProps.visible = false">取消</el-button>
      <el-button
        v-show="!drawerProps.isView"
        type="primary"
        @click="handleSubmit"
        >确定</el-button
      >
    </template>
  </el-drawer>
</template>

<script setup name="EditDrawer">
import "plus-pro-components/es/components/drawer-form/style/css";
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";

<#if picture>import UploadImg from "@/components/Upload/Img.vue";</#if>
<#if pictures>import UploadImgMul from "@/components/Upload/Imgs.vue";</#if>
<#if file>import UploadFiles from "@/components/Upload/Files.vue";</#if>
<#if richtext>import WangEditor from "@/components/Editor/WangEditor.vue";</#if>

import { enums } from "@/api/sys/common";
import { add, edit } from "./${beanName}";
<#list columnList as c>
  <#if c.formType=='enum'>
const ${c.property}Enums = ref([]);
  </#if>
</#list>
onMounted(() => {
<#list columnList as c>
  <#if c.formType=='enum'>
  enums("${c.columnRemark}").then(res => {
    ${c.property}Enums.value = res.data;
  });
  </#if>
</#list>
});

const rules = reactive({
<#list columnList as c>
  <#if c.required==1>
    <#if c.formType=='phone'>
  ${c.property}: [{ required: true, pattern: /^\d{11}$/, message: "${c.label}格式错误" }],
    <#elseif c.formType=='enum' || c.formType=='select' || c.formType=='radio' || c.formType=='checkbox'>
  ${c.property}: [{ required: true, message: "请选择${c.label}" }],
    <#elseif c.formType=='picture' || c.formType=='pictures' || c.formType=='file'>
  ${c.property}: [{ required: true, message: "请上传${c.label}" }],
    <#else>
  ${c.property}: [{ required: true, message: "请填写${c.label}" }],
    </#if>
  <#elseif c.formType=='phone'>
  ${c.property}: [{ required: false, pattern: /^\d{11}$/, message: "${c.label}格式错误" }],
  </#if>
</#list>
});

const drawerProps = ref({
  title: "",
  isView: false,
  visible: false
  //getTableList
});
const row = ref({});

// 接收父组件传过来的参数
const acceptParams = (rowData, getTableList, isView) => {
  row.value = rowData;
  drawerProps.value.getTableList = getTableList;
  drawerProps.value.isView = isView;
  if (isView) {
    drawerProps.value.title = "查看${tableRemark}";
  } else {
    drawerProps.value.title = rowData.id ? "编辑${tableRemark}" : "新增${tableRemark}";
  }
  drawerProps.value.visible = true;
};

// 提交数据（新增/编辑）
const ruleFormRef = ref();
const handleSubmit = () => {
  ruleFormRef.value.validate(async valid => {
    if (!valid) return;
    try {
      const api = row.value && row.value.id ? edit : add;
      await api(row.value);
      ElMessage.success({ message: `${r'${drawerProps.value.title}成功！'}` });
      drawerProps.value.getTableList();
      drawerProps.value.visible = false;
    } catch (error) {
      console.log(error);
    }
  });
};
<#if date>
const disabledDateFun = time => {
  return time.getTime() < Date.now();
};
</#if>
<#if datetime>
const disabledDateTimeFun = time => {
  return time.getTime() < Date.now() - 3600 * 1000 * 24;
};
</#if>
defineExpose({
  acceptParams
});
</script>
