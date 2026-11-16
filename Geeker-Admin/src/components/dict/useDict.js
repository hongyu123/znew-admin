import { onMounted, ref, watch } from "vue";

import { dict } from "@/api/modules/common";

/**
 * 字典项缓存
 * dictKey -> Promise<字典项数组>
 * 同一字典在多个组件中同时使用（或重复挂载）时只会请求一次
 */
const dictCache = new Map();

/**
 * 加载字典项
 * @param {string} dictKey 字典类型（对应 sys_dictionary.dict_key）
 * @returns {Promise<Array<{ id: number, value: string, label: string }>>}
 */
export const loadDict = dictKey => {
  if (!dictKey) return Promise.resolve([]);
  if (!dictCache.has(dictKey)) {
    const request = dict(dictKey)
      .then(res => res.data || [])
      .catch(err => {
        // 请求失败时清除缓存，下次可重新请求
        dictCache.delete(dictKey);
        throw err;
      });
    dictCache.set(dictKey, request);
  }
  return dictCache.get(dictKey);
};

/**
 * 清除字典缓存
 * 字典维护页面增删改字典项后可调用，不传 dictKey 时清空全部缓存
 * @param {string} [dictKey]
 */
export const clearDictCache = dictKey => {
  if (dictKey) {
    dictCache.delete(dictKey);
  } else {
    dictCache.clear();
  }
};

/**
 * 字典项加载（组件内使用）
 * @param {import("vue").Ref<string>} dictKey 字典类型
 * @returns {{ options: import("vue").Ref<Array<object>>, loading: import("vue").Ref<boolean>, reload: (force?: boolean) => void }}
 */
export const useDictOptions = dictKey => {
  const options = ref([]);
  const loading = ref(false);

  /**
   * 重新加载字典项
   * @param {boolean} force 是否强制清除缓存后重新请求
   */
  const reload = (force = false) => {
    const key = dictKey.value;
    if (!key) {
      options.value = [];
      loading.value = false;
      return;
    }
    if (force) clearDictCache(key);
    loading.value = true;
    loadDict(key)
      .then(list => {
        // dictKey 在请求期间发生变化时，丢弃旧结果
        if (dictKey.value === key) options.value = list;
      })
      .catch(() => {
        if (dictKey.value === key) options.value = [];
      })
      .finally(() => {
        if (dictKey.value === key) loading.value = false;
      });
  };

  watch(dictKey, () => reload());
  onMounted(() => reload());

  return { options, loading, reload };
};
