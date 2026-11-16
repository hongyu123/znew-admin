<template>
  <!-- 数据字典下拉选择：:dict-key 传字典类型，v-model 绑定值，multiple 控制单选/多选 -->
  <el-select
    v-model="innerValue"
    class="dict-select"
    :multiple="multiple"
    :multiple-limit="multipleLimit"
    :disabled="disabled"
    :clearable="clearable"
    :filterable="filterable"
    :collapse-tags="multiple && collapseTags"
    :placeholder="placeholder"
    :loading="loading"
    @change="handleChange"
  >
    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>

<script setup lang="js" name="DictSelect">
import { computed, toRef } from "vue";

import { useDictOptions } from "./useDict";

const props = defineProps({
  /** 绑定值，多选时为数组 */
  modelValue: { type: [String, Number, Array], default: undefined },
  /** 字典类型（对应 sys_dictionary.dict_key） */
  dictKey: { type: String, required: true },
  /** 是否为多选 */
  multiple: { type: Boolean, default: false },
  /** 多选时最多可选的项数，0 表示不限制 */
  multipleLimit: { type: Number, default: 0 },
  /** 是否可清空 */
  clearable: { type: Boolean, default: true },
  /** 是否可搜索 */
  filterable: { type: Boolean, default: true },
  /** 多选时是否折叠显示已选项 */
  collapseTags: { type: Boolean, default: true },
  /** 是否禁用 */
  disabled: { type: Boolean, default: false },
  /** 占位提示 */
  placeholder: { type: String, default: "请选择" }
});

const emit = defineEmits(["update:modelValue", "change"]);

const { options, loading, reload } = useDictOptions(toRef(props, "dictKey"));

// 统一单选/多选绑定值的类型：单选为空串，多选为数组
const innerValue = computed({
  get() {
    const value = props.modelValue;
    if (!props.multiple) return value ?? "";
    if (Array.isArray(value)) return value;
    return value === undefined || value === null || value === "" ? [] : [value];
  },
  set(value) {
    emit("update:modelValue", value);
  }
});

const handleChange = value => {
  emit("change", value);
};

defineExpose({ reload });
</script>
