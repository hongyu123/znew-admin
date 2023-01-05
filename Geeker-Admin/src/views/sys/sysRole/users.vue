<template>
  <div class="table-box">
    <ProTable ref="proTable" title="角色授权用户列表" :columns="columns" :requestApi="getTableList">
      <!-- 表格 header 按钮 -->
      <template #tableHeader="scope">
        <el-button type="primary" :icon="CirclePlus" @click="selectDataVisible = true">新增</el-button>
        <el-button type="danger" plain @click="delsModel(scope.selectedListIds)" :disabled="!scope.isSelected">
          取消授权
        </el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="danger" link @click="delModel(scope.row)">取消授权</el-button>
      </template>
    </ProTable>

    <el-dialog v-model="selectDataVisible" :destroy-on-close="true" width="50%" title="选择用户" draggable>
      <AppUserSelect ref="selectDataRef"></AppUserSelect>
      <template #footer>
        <el-button @click="selectDataVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSelectData">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="tsx" name="useProTable">
import { ref } from "vue";
import { ColumnProps } from "@/components/ProTable/interface";
import ProTable from "@/components/ProTable/index.vue";
import { CirclePlus } from "@element-plus/icons-vue";
import { users, addUsers, delUsers } from "@/api/sys/sysRole";
import { useHandleData } from "@/hooks/useHandleData";
import { EnableStateEnum } from "@/api/modules/enum";
import { useRoute } from "vue-router";
import AppUserSelect from "@/views/sys/sysUser/select.vue";
const route = useRoute();

// 获取 ProTable 元素，调用其获取刷新数据方法（还能获取到当前查询参数，方便导出携带参数）
const proTable = ref();

// 如果你想在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 ProTable 组件上绑定	:requestApi="getUserList"
const getTableList = (params: any) => {
  if (route.params.roleId != ":roleId") {
    params.roleId = route.params.roleId;
  }
  return users(params);
};

// 表格配置项
const columns: ColumnProps[] = [
  { type: "selection", fixed: "left", width: 50 },
  {
    prop: "account",
    label: "账号",
    search: { el: "input", key: "account_like" }
  },
  {
    prop: "nickname",
    label: "昵称",
    search: { el: "input", key: "nickname_like" }
  },
  {
    prop: "phone",
    label: "手机号码",
    search: { el: "input", key: "phone_like" }
  },
  {
    prop: "avatar",
    label: "头像",
    render: (scope: { row: any }) => {
      return (
        <>
          <el-avatar shape="square" fit="contain" src={scope.row.avatar}></el-avatar>
        </>
      );
    }
  },
  {
    prop: "state",
    label: "状态",
    enum: EnableStateEnum,
    fieldNames: { label: "desc", value: "value" },
    search: { el: "select" }
  },
  { prop: "operation", label: "操作", fixed: "right", width: 220 }
];

//删除
const delModel = async (params: any) => {
  await useHandleData(delUsers, { roleId: route.params.roleId, userIds: [params.id] }, `取消授权用户【${params.account}】`);
  proTable.value.getTableList();
};
// 批量删除
const delsModel = async (ids: string[]) => {
  await useHandleData(delUsers, { roleId: route.params.roleId, userIds: ids }, "取消授权用户");
  proTable.value.clearSelection();
  proTable.value.getTableList();
};

//选择弹出框
const selectDataVisible = ref(false);
const selectDataRef = ref();
const handleSelectData = async () => {
  const selectData = selectDataRef.value.getSelect();
  const ids = selectData.map((data: any) => data.id);
  await addUsers({ roleId: route.params.roleId, userIds: ids });
  selectDataVisible.value = false;
  proTable.value.getTableList();
};
</script>
