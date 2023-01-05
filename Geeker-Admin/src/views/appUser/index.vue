<template>
  <div class="table-box">
    <ProTable ref="proTable" title="标签列表" :columns="columns" :requestApi="getTableList" :toolButton="false">
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="View" link @click="openEditForm('查看', scope.row)">详情</el-button>
      </template>
    </ProTable>

    <EditModelForm ref="editModelFormRef" />
  </div>
</template>

<script setup lang="tsx" name="useProTable">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import EditModelForm from "@/views/appUser/edit.vue";
import { page, save, edit, state } from "@/api/modules/appUser";
import { EnableStateEnum } from "@/api/modules/enum";
import { ElMessage, ElMessageBox } from "element-plus";

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  params.sortByField = params.sortByField ?? "id";
  params.sortByWay = params.sortByWay ?? "desc";
  return page(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  //{ type: "selection", fixed: "left", width: 80 },
  {
    prop: "id",
    label: "ID"
  },
  {
    prop: "nickname",
    label: "昵称",
    search: { el: "input", key: "nickname_like" }
  },
  {
    prop: "photo",
    label: "头像",
    render: (scope: { row: any }) => {
      return (
        <>
          <el-avatar shape="square" fit="contain" src={scope.row.photo}></el-avatar>
        </>
      );
    }
  },
  {
    prop: "phone",
    label: "手机号码",
    search: { el: "input", key: "phone_like" },
    width: 120
  },
  {
    prop: "genderDesc",
    label: "性别"
  },
  {
    prop: "focusNum",
    label: "关注数量"
  },
  {
    prop: "fansNum",
    label: "粉丝数量"
  },
  {
    prop: "state",
    label: "状态",
    enum: EnableStateEnum,
    fieldNames: { label: "desc", value: "value" },
    search: { el: "select" },
    render: (scope: { row: any }) => {
      return (
        <>
          <el-switch
            model-value={scope.row.state}
            active-text={scope.row.state == "Enable" ? "启用" : "禁用"}
            active-value={"Enable"}
            inactive-value={"Disable"}
            onClick={() => changeStatus(scope.row)}
          />
        </>
      );
    }
  },
  {
    prop: "comment",
    label: "备注"
  },
  { prop: "operation", label: "操作", fixed: "right", width: 120 }
];

// 新增、查看、编辑
const editModelFormRef = ref();
const openEditForm = (title: string, rowData: Partial<any> = {}) => {
  let params = {
    title,
    rowData: { ...rowData },
    isView: title === "查看",
    api: title === "新增" ? save : title === "编辑" ? edit : "",
    getTableList: proTable.value.getTableList
  };
  editModelFormRef.value.acceptParams(params);
};

// 切换用户状态
const changeStatus = async (row: any) => {
  const optDesc = row.state == "Enable" ? "禁用" : "启用";
  ElMessageBox.prompt("请输入备注!", optDesc, {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    inputPattern: /^.{0,200}$/,
    inputErrorMessage: "内容过长"
  })
    .then(({ value }) => {
      if (!value) {
        value = "";
      }
      state({ id: row.id, state: row.state == "Enable" ? "Disable" : "Enable", comment: value }).then(() => {
        ElMessage({
          type: "success",
          message: `${optDesc}成功!`
        });
        proTable.value.getTableList();
      });
    })
    .catch(() => {});
};
</script>
