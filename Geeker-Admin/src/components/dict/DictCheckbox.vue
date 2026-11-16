<template>
  <!-- 数据字典多选框：:dict-key 传字典类型，v-model 绑定数组 -->
  <el-checkbox-group
    v-model="innerValue"
    v-loading="loading"
    class="dict-checkbox"
    :disabled="disabled"
    :min="min"
    :max="max"
    @change="handleChange"
  >
    <el-checkbox
      v-for="item in options"
      :key="item.value"
      :value="item.value"
      :border="border"
      :disabled="disabled || item.disabled"
    >
      {{ item.label }}
    </el-checkbox>
  </el-checkbox-group>
</template>

<script setup lang="js" name="DictCheckbox">
import { computed, toRef } from "vue";

import { useDictOptions } from "./useDict";

const props = defineProps({
  /** 绑定值（数组） */
  modelValue: { type: Array, default: () => [] },
  /** 字典类型（对应 sys_dictionary.dict_key） */
  dictKey: { type: String, required: true },
  /** 是否禁用 */
  disabled: { type: Boolean, default: false },
  /** 是否显示边框 */
  border: { type: Boolean, default: false },
  /** 可勾选的最少数量 */
  min: { type: Number, default: undefined },
  /** 可勾选的最多数量 */
  max: { type: Number, default: undefined }
});

const emit = defineEmits(["update:modelValue", "change"]);

const { options, loading, reload } = useDictOptions(toRef(props, "dictKey"));

// 统一绑定值类型为数组，兼容外部传入单值的情况
const innerValue = computed({
  get() {
    const value = props.modelValue;
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

<style scoped lang="scss">
.dict-checkbox {
  display: inline-flex;
  flex-wrap: wrap;
  align-items: center;
  min-height: 32px;
}
</style>
