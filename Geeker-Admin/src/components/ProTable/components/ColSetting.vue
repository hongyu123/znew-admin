<template>
  <!-- 列设置 -->
  <el-drawer v-model="drawerVisible" title="列设置" size="450px" @opened="initSortable">
    <div class="table-main">
      <div class="col-setting-tip">按住行拖动可调整列顺序</div>
      <el-table ref="tableRef" :data="colSetting" :border="true" row-key="prop">
        <el-table-column width="50" align="center">
          <template #default>
            <el-icon class="drag-icon"><Rank /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="label" align="center" label="列名" />
        <el-table-column v-slot="scope" prop="isShow" align="center" label="显示">
          <el-switch v-model="scope.row.isShow"></el-switch>
        </el-table-column>
        <template #empty>
          <div class="table-empty">
            <img src="@/assets/images/notData.png" alt="notData" />
            <div>暂无可配置列</div>
          </div>
        </template>
      </el-table>
    </div>
    <template #footer v-if="showFooter">
      <el-button @click="handleReset">重置</el-button>
      <el-button type="primary" @click="handleSave">保存</el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts" name="ColSetting">
import { Rank } from "@element-plus/icons-vue";
import { ElTable } from "element-plus";
import Sortable from "sortablejs";
import { nextTick, onBeforeUnmount, ref } from "vue";

import { ColumnProps } from "@/components/ProTable/interface";

defineProps<{ colSetting: ColumnProps[]; showFooter?: boolean }>();

// 保存 / 重置 / 拖动排序事件交由 ProTable 处理
const emit = defineEmits<{
  save: [];
  reset: [];
  reorder: [{ oldIndex: number; newIndex: number }];
}>();

const tableRef = ref<InstanceType<typeof ElTable>>();
const drawerVisible = ref<boolean>(false);
let sortable: Sortable | null = null;

const openColSetting = async () => {
  drawerVisible.value = true;
  await nextTick();
  initSortable();
};

// 初始化行拖拽排序：鼠标停留在行上即可拖动调整列顺序
const initSortable = () => {
  if (sortable) return;
  const tbody = tableRef.value?.$el?.querySelector(".el-table__body-wrapper tbody") as HTMLElement | undefined;
  if (!tbody) return;
  sortable = Sortable.create(tbody, {
    animation: 300,
    ghostClass: "drag-ghost",
    filter: ".el-switch", // 开关区域不触发拖拽
    preventOnFilter: false,
    onEnd: ({ oldIndex, newIndex }) => {
      if (oldIndex === undefined || newIndex === undefined || oldIndex === newIndex) return;
      emit("reorder", { oldIndex, newIndex });
    }
  });
};

const handleSave = () => {
  emit("save");
  drawerVisible.value = false;
};

const handleReset = () => {
  emit("reset");
};

onBeforeUnmount(() => {
  sortable?.destroy();
  sortable = null;
});

defineExpose({
  openColSetting
});
</script>

<style scoped lang="scss">
.table-main {
  .col-setting-tip {
    margin-bottom: 10px;
    font-size: 12px;
    color: var(--el-text-color-secondary);
  }
  :deep(.el-table__body tr) {
    cursor: move;
    user-select: none;
  }
}
.drag-icon {
  color: var(--el-text-color-secondary);
}
:deep(.drag-ghost) {
  opacity: 0.6;
}
</style>
