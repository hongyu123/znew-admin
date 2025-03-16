import { ref } from "vue";

/**
 *
 * @param {*} tableRef 表格ref
 * @param {*} getTableList 表格获取数据方法
 * @returns
 */
export function useQueryTable(tableRef, getTableList) {
  //查询参数
  const queryParams = ref({
    pageNumber: 1,
    pageSize: 10,
    params: {}
  });
  //是否显示搜索表单
  const showSearch = ref(true);

  /** 表单搜索 */
  const handleSearch = () => {
    getTableList();
  };
  /** 搜索表单重置 */
  const handleReset = () => {
    queryParams.value = {
      pageNumber: 1,
      pageSize: 10,
      params: {}
    };
    getTableList();
  };
  /** 显示/隐藏搜素表单 */
  const onShowSearch = () => {
    showSearch.value = !showSearch.value;
  };

  //表格数据
  const loading = ref(false);
  const total = ref(0);
  const dataList = ref([]);
  const selectedData = ref([]);

  /** 表格排序 */
  const handleSortChange = ({ column, prop, order }) => {
    if (order) {
      queryParams.value.sortByWay = order == "ascending" ? "asc" : "desc";
      queryParams.value.sortByField =
        column.sortable === "string" ? column.sortable : prop;
    } else {
      queryParams.value.sortByWay = undefined;
      queryParams.value.sortByField = undefined;
    }
    getTableList();
  };

  /** 表格分页 */
  function handleCurrentChange(current) {
    queryParams.value.pageNumber = current;
    getTableList();
  }
  function handleSizeChange(size) {
    queryParams.value.pageSize = size;
    getTableList();
  }

  /** 表格多选事件 */
  function handleSelectionChange(selected) {
    selectedData.value = selected;
  }

  /** 表格取消选中 */
  function onSelectionCancel() {
    selectedData.value = [];
    tableRef.value.getTableRef().clearSelection();
  }

  return {
    queryParams,
    showSearch,
    handleSearch,
    handleReset,
    onShowSearch,
    loading,
    total,
    dataList,
    selectedData,
    handleSortChange,
    handleCurrentChange,
    handleSizeChange,
    handleSelectionChange,
    onSelectionCancel
  };
}
