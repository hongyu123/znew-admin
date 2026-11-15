<!-- 📚📚📚 Pro-Table 文档: https://juejin.cn/post/7166068828202336263 -->

<template>
  <!-- 查询表单 -->
  <SearchForm
    v-show="isShowSearch"
    :search="_search"
    :reset="_reset"
    :columns="searchColumns"
    :search-param="searchParam"
    :search-col="searchCol"
  />

  <!-- 表格主体 -->
  <div class="card table-main">
    <!-- 表格头部 操作按钮 -->
    <div class="table-header">
      <div class="header-button-lf">
        <slot name="tableHeader" :selected-list="selectedList" :selected-list-ids="selectedListIds" :is-selected="isSelected" />
      </div>
      <div v-if="toolButton" class="header-button-ri">
        <slot name="toolButton">
          <el-button v-if="showToolButton('refresh')" :icon="Refresh" circle @click="getTableList" />
          <el-button v-if="showToolButton('setting') && columns.length" :icon="Operation" circle @click="openColSetting" />
          <el-button
            v-if="showToolButton('search') && searchColumns?.length"
            :icon="Search"
            circle
            @click="isShowSearch = !isShowSearch"
          />
        </slot>
      </div>
    </div>
    <!-- 表格主体 -->
    <el-table
      v-bind="$attrs"
      :id="uuid"
      ref="tableRef"
      :data="processTableData"
      :border="border"
      :row-key="rowKey"
      @selection-change="selectionChange"
      @sort-change="sortChange"
    >
      <!-- 默认插槽 -->
      <slot />
      <template v-for="item in tableColumns" :key="item">
        <!-- selection || radio || index || expand || sort -->
        <el-table-column
          v-if="item.type && columnTypes.includes(item.type)"
          v-bind="item"
          :align="item.align ?? 'center'"
          :reserve-selection="item.type == 'selection'"
        >
          <template #default="scope">
            <!-- expand -->
            <template v-if="item.type == 'expand'">
              <component :is="item.render" v-bind="scope" v-if="item.render" />
              <slot v-else :name="item.type" v-bind="scope" />
            </template>
            <!-- radio -->
            <el-radio v-if="item.type == 'radio'" v-model="radio" :label="scope.row[rowKey]">
              <i></i>
            </el-radio>
            <!-- sort -->
            <el-tag v-if="item.type == 'sort'" class="move">
              <el-icon> <DCaret /></el-icon>
            </el-tag>
          </template>
        </el-table-column>
        <!-- other -->
        <TableColumn v-else :column="item">
          <template v-for="slot in Object.keys($slots)" #[slot]="scope">
            <slot :name="slot" v-bind="scope" />
          </template>
        </TableColumn>
      </template>
      <!-- 插入表格最后一行之后的插槽 -->
      <template #append>
        <slot name="append" />
      </template>
      <!-- 无数据 -->
      <template #empty>
        <div class="table-empty">
          <slot name="empty">
            <img src="@/assets/images/notData.png" alt="notData" />
            <div>暂无数据</div>
          </slot>
        </div>
      </template>
    </el-table>
    <!-- 分页组件 -->
    <slot name="pagination">
      <Pagination
        v-if="pagination"
        :pageable="pageable"
        :handle-size-change="handleSizeChange"
        :handle-current-change="handleCurrentChange"
      />
    </slot>
  </div>
  <!-- 列设置 -->
  <ColSetting
    v-if="toolButton"
    ref="colRef"
    :col-setting="colSetting"
    :show-footer="!!props.colSettingKey"
    @save="saveColSetting"
    @reset="resetColSetting"
    @reorder="handleColReorder"
  />
</template>

<script setup lang="ts" name="ProTable">
import { Operation, Refresh, Search } from "@element-plus/icons-vue";
import { ElMessage, ElTable } from "element-plus";
import Sortable from "sortablejs";
import { computed, onMounted, provide, reactive, ref, unref, watch } from "vue";

import { BreakPoint } from "@/components/Grid/interface";
import { ColumnProps, TypeProps } from "@/components/ProTable/interface";
import SearchForm from "@/components/SearchForm/index.vue";
import { useSelection } from "@/hooks/useSelection";
import { useTable } from "@/hooks/useTable";
import { generateUUID, handleProp } from "@/utils";

import ColSetting from "./components/ColSetting.vue";
import Pagination from "./components/Pagination.vue";
import TableColumn from "./components/TableColumn.vue";

export interface ProTableProps {
  columns: ColumnProps[]; // 列配置项  ==> 必传
  data?: any[]; // 静态 table data 数据，若存在则不会使用 requestApi 返回的 data ==> 非必传
  requestApi?: (params: any) => Promise<any>; // 请求表格数据的 api ==> 非必传
  requestAuto?: boolean; // 是否自动执行请求 api ==> 非必传（默认为true）
  requestError?: (params: any) => void; // 表格 api 请求错误监听 ==> 非必传
  dataCallback?: (data: any) => any; // 返回数据的回调函数，可以对数据进行处理 ==> 非必传
  title?: string; // 表格标题 ==> 非必传
  pagination?: boolean; // 是否需要分页组件 ==> 非必传（默认为true）
  initParam?: any; // 初始化请求参数 ==> 非必传（默认为{}）
  border?: boolean; // 是否带有纵向边框 ==> 非必传（默认为true）
  toolButton?: ("refresh" | "setting" | "search")[] | boolean; // 是否显示表格功能按钮 ==> 非必传（默认为true）
  rowKey?: string; // 行数据的 Key，用来优化 Table 的渲染，当表格数据多选时，所指定的 id ==> 非必传（默认为 id）
  searchCol?: number | Record<BreakPoint, number>; // 表格搜索项 每列占比配置 ==> 非必传 { xs: 1, sm: 2, md: 2, lg: 3, xl: 4 }
  colSettingKey?: string; // 列设置持久化标识，为空时不显示保存/重置按钮，也不读写本地缓存 ==> 非必传
}

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<ProTableProps>(), {
  columns: () => [],
  requestAuto: true,
  pagination: true,
  initParam: {},
  border: true,
  toolButton: true,
  rowKey: "id",
  searchCol: () => ({ xs: 1, sm: 2, md: 3, lg: 4, xl: 5 })
});

// table 实例
const tableRef = ref<InstanceType<typeof ElTable>>();

// 生成组件唯一id
const uuid = ref("id-" + generateUUID());

// column 列类型
const columnTypes: TypeProps[] = ["selection", "radio", "index", "expand", "sort"];

// 是否显示搜索模块
const isShowSearch = ref(true);

// 控制 ToolButton 显示
const showToolButton = (key: "refresh" | "setting" | "search") => {
  return Array.isArray(props.toolButton) ? props.toolButton.includes(key) : props.toolButton;
};

// 单选值
const radio = ref("");

// 表格多选 Hooks
const { selectionChange, selectedList, selectedListIds, isSelected } = useSelection(props.rowKey);

// 表格操作 Hooks
const { tableData, pageable, searchParam, searchInitParam, getTableList, search, reset, handleSizeChange, handleCurrentChange } =
  useTable(props.requestApi, props.initParam, props.pagination, props.dataCallback, props.requestError);

// 清空选中数据列表
const clearSelection = () => tableRef.value!.clearSelection();

// 初始化表格数据 && 拖拽排序
onMounted(() => {
  dragSort();
  if (props.requestAuto) {
    getTableList();
  }

  if (props.data) {
    pageable.value.total = props.data.length;
  }
});

// 处理表格数据
const processTableData = computed(() => {
  if (!props.data) return tableData.value;
  if (!props.pagination) return props.data;
  return props.data.slice(
    (pageable.value.pageNum - 1) * pageable.value.pageSize,
    pageable.value.pageSize * pageable.value.pageNum
  );
});

// 监听页面 initParam 改化，重新获取表格数据
watch(() => props.initParam, getTableList, { deep: true });

// 接收 columns 并设置为响应式
const tableColumns = reactive<ColumnProps[]>(props.columns);

// 扁平化 columns
const flatColumns = computed(() => flatColumnsFunc(tableColumns));

// 定义 enumMap 存储 enum 值（避免异步请求无法格式化单元格内容 || 无法填充搜索下拉选择）
const enumMap = ref(new Map<string, { [key: string]: any }[]>());
const setEnumMap = async ({ prop, enum: enumValue }: ColumnProps) => {
  if (!enumValue) return;

  // 如果当前 enumMap 存在相同的值 return
  if (enumMap.value.has(prop!) && (typeof enumValue === "function" || enumMap.value.get(prop!) === enumValue)) return;

  // 当前 enum 为静态数据，则直接存储到 enumMap
  if (typeof enumValue !== "function") return enumMap.value.set(prop!, unref(enumValue!));

  // 为了防止接口执行慢，而存储慢，导致重复请求，所以预先存储为[]，接口返回后再二次存储
  enumMap.value.set(prop!, []);

  // 当前 enum 为后台数据需要请求数据，则调用该请求接口，并存储到 enumMap
  const { data } = await enumValue();
  enumMap.value.set(prop!, data);
};

// 注入 enumMap
provide("enumMap", enumMap);

// 扁平化 columns 的方法
const flatColumnsFunc = (columns: ColumnProps[], flatArr: ColumnProps[] = []) => {
  columns.forEach(async col => {
    if (col._children?.length) flatArr.push(...flatColumnsFunc(col._children));
    flatArr.push(col);

    // column 添加默认 isShow && isSetting && isFilterEnum 属性值
    col.isShow = col.isShow ?? true;
    col.isSetting = col.isSetting ?? true;
    col.isFilterEnum = col.isFilterEnum ?? true;

    // 设置 enumMap
    await setEnumMap(col);
  });
  return flatArr.filter(item => !item._children?.length);
};

// 过滤需要搜索的配置项 && 排序
const searchColumns = computed(() => {
  return flatColumns.value
    ?.filter(item => item.search?.el || item.search?.render)
    .sort((a, b) => a.search!.order! - b.search!.order!);
});

// 设置 搜索表单默认排序 && 搜索表单项的默认值
searchColumns.value?.forEach((column, index) => {
  column.search!.order = column.search?.order ?? index + 2;
  const key = column.search?.key ?? handleProp(column.prop!);
  const defaultValue = column.search?.defaultValue;
  if (defaultValue !== undefined && defaultValue !== null) {
    searchParam.value[key] = defaultValue;
    searchInitParam.value[key] = defaultValue;
  }
});

// 列设置 ==> 需要过滤掉不需要设置的列
const colRef = ref();
const openColSetting = () => colRef.value.openColSetting();

// 列设置持久化标识（未设置 colSettingKey 时不启用本地缓存）
const colSettingStorageKey = computed(() => (props.colSettingKey ? `pro-table-col-setting:${props.colSettingKey}` : ""));

// 当前列是否参与列设置
const isConfigurableColumn = (column: ColumnProps) => {
  const { type, prop, isSetting } = column;
  return !columnTypes.includes(type!) && prop !== "operation" && unref(isSetting) !== false;
};

// 列设置列表（顺序跟随 tableColumns）
const colSetting = computed(() => tableColumns.filter(isConfigurableColumn));

// 记录初始列顺序与显示状态，用于重置
const colSettingDefault = ref<{ prop: string; isShow: boolean }[]>(
  colSetting.value.map(column => ({ prop: column.prop!, isShow: unref(column.isShow) !== false }))
);

// 将列设置应用到表格列（顺序 + 显示状态）
const applyColSetting = (setting: { prop: string; isShow?: boolean }[]) => {
  const orderMap = new Map(setting.map((item, index) => [item.prop, index]));
  const showMap = new Map(setting.map(item => [item.prop, item.isShow]));
  const settingColumns = tableColumns.filter(isConfigurableColumn);

  // 恢复显示状态
  settingColumns.forEach(column => {
    if (showMap.has(column.prop!)) column.isShow = showMap.get(column.prop!) !== false;
  });

  // 按保存的顺序排序（新增列保持原有相对位置，并排在已保存列之后）
  const sortedColumns = [...settingColumns].sort((a, b) => {
    const aIndex = orderMap.get(a.prop!);
    const bIndex = orderMap.get(b.prop!);
    if (aIndex === undefined && bIndex === undefined) return 0;
    if (aIndex === undefined) return 1;
    if (bIndex === undefined) return -1;
    return aIndex - bIndex;
  });

  // 将新顺序写回 tableColumns，特殊列（selection、index、operation 等）位置保持不变
  let cursor = 0;
  tableColumns.forEach((column, index) => {
    if (isConfigurableColumn(column)) tableColumns[index] = sortedColumns[cursor++];
  });
};

// 读取本地保存的列设置
const loadColSetting = () => {
  if (!colSettingStorageKey.value) return;
  try {
    const setting = JSON.parse(localStorage.getItem(colSettingStorageKey.value) || "null");
    if (Array.isArray(setting)) applyColSetting(setting);
  } catch (error) {
    console.warn("读取列设置失败", error);
  }
};
loadColSetting();

// 保存列设置到 localStorage
const saveColSetting = () => {
  if (!colSettingStorageKey.value) return;
  const setting = colSetting.value.map(column => ({ prop: column.prop!, isShow: unref(column.isShow) !== false }));
  localStorage.setItem(colSettingStorageKey.value, JSON.stringify(setting));
  ElMessage.success("列设置已保存");
};

// 重置列设置（恢复初始顺序与显示状态，并清除本地缓存）
const resetColSetting = () => {
  applyColSetting(colSettingDefault.value);
  if (colSettingStorageKey.value) localStorage.removeItem(colSettingStorageKey.value);
  ElMessage.success("列设置已重置");
};

// 拖拽列设置行，同步调整表格列顺序
const handleColReorder = ({ oldIndex, newIndex }: { oldIndex: number; newIndex: number }) => {
  const settingColumns = [...colSetting.value];
  const [movedColumn] = settingColumns.splice(oldIndex, 1);
  if (!movedColumn) return;
  settingColumns.splice(newIndex, 0, movedColumn);

  // 将可配置列的新顺序写回 tableColumns，特殊列位置保持不变
  let cursor = 0;
  tableColumns.forEach((column, index) => {
    if (isConfigurableColumn(column)) tableColumns[index] = settingColumns[cursor++];
  });
};

// 定义 emit 事件
const emit = defineEmits<{
  search: [];
  reset: [];
  dragSort: [{ newIndex?: number; oldIndex?: number }];
}>();

const _search = () => {
  search();
  emit("search");
};

const _reset = () => {
  reset();
  emit("reset");
};

// 表格拖拽排序
const dragSort = () => {
  const tbody = document.querySelector(`#${uuid.value} tbody`) as HTMLElement;
  Sortable.create(tbody, {
    handle: ".move",
    animation: 300,
    onEnd({ newIndex, oldIndex }) {
      const [removedItem] = processTableData.value.splice(oldIndex!, 1);
      processTableData.value.splice(newIndex!, 0, removedItem);
      emit("dragSort", { newIndex, oldIndex });
    }
  });
};

const sortChange = (data, prop, order) => {
  const column = data.column;
  searchParam.value["sortByField"] = data.prop;
  if (typeof column.sortable === "string") {
    searchParam.value["sortByField"] = column.sortable;
  }
  searchParam.value["sortByWay"] = "ascending" === data.order ? "ASC" : "DESC";
  _search();
};

// 暴露给父组件的参数和方法 (外部需要什么，都可以从这里暴露出去)
defineExpose({
  element: tableRef,
  tableData: processTableData,
  radio,
  pageable,
  searchParam,
  searchInitParam,
  isSelected,
  selectedList,
  selectedListIds,

  // 下面为 function
  getTableList,
  search,
  reset,
  handleSizeChange,
  handleCurrentChange,
  clearSelection,
  enumMap
});
</script>
