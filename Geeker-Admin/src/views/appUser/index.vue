<template>
  <div class="table-box">
    <ProTable ref="proTable" title="app用户列表" :columns="columns" :requestApi="getTableList" :toolButton="false">
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" icon="View" link @click="openEditForm('查看', scope.row)" v-auth="['detail']">详情</el-button>
      </template>
    </ProTable>

    <EditModelForm ref="editModelFormRef" />
  </div>
</template>

<script setup lang="tsx" name="AppUser">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import EditModelForm from "@/views/appUser/edit.vue";
import { page, add, edit, state } from "@/api/modules/appUser";
import { GenderEnum } from "@/api/modules/enum";
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
  // {
  //   prop: "id",
  //   label: "ID"
  // },
  {
    prop: "nickname",
    label: "昵称",
    search: { el: "input", key: "nickname_like" }
  },
  {
    prop: "avatar",
    label: "头像",
    render: (scope: { row: any }) => {
      return <el-avatar shape="square" fit="contain" src={scope.row.avatar}></el-avatar>;
    }
  },
  {
    prop: "phone",
    label: "手机号码",
    width: 120,
    search: { el: "input", key: "phone_like" }
  },
  {
    prop: "gender",
    label: "性别",
    width: 110,
    enum: GenderEnum
  },
  {
    prop: "birth",
    label: "生日",
    width: 110
  },
  {
    prop: "address",
    label: "地址"
  },
  {
    prop: "enableState",
    label: "启用状态",
    width: 110,
    search: { el: "select", key: "enableState" },
    enum: [
      { label: "启用", value: "Enable" },
      { label: "禁用", value: "Disable" }
    ],
    render: (scope: { row: any }) => {
      return (
        <el-switch
          model-value={scope.row.enableState}
          active-text={scope.row.enableState == "Enable" ? "启用" : "禁用"}
          active-value={"Enable"}
          inactive-value={"Disable"}
          onClick={() => changeStatus(scope.row)}
        />
      );
    }
  },
  {
    prop: "comment",
    label: "备注"
  },
  { prop: "operation", label: "操作", fixed: "right", width: 200 }
];

// 新增、查看、编辑
const editModelFormRef = ref();
const openEditForm = (title: string, rowData: Partial<any> = {}) => {
  let params = {
    title,
    rowData: { ...rowData },
    isView: title === "查看",
    api: title === "新增" ? add : title === "编辑" ? edit : "",
    getTableList: proTable.value.getTableList
  };
  editModelFormRef.value.acceptParams(params);
};

//启用/禁用
const changeStatus = async (row: any) => {
  const optDesc = row.enableState == "Enable" ? "禁用" : "启用";
  ElMessageBox.prompt("请输入备注!", optDesc, {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    inputPattern: /^.{0,200}$/,
    inputErrorMessage: "内容过长"
  })
    .then(({ value }) => {
      let params: any = { id: row.id, enableState: row.enableState == "Enable" ? "Disable" : "Enable" };
      if (value) {
        params.comment = value;
      }
      state(params).then(() => {
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
