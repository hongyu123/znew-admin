<template>
  <div>
    <button @click="expand">展开</button>
    <el-table
      highlight-current-row
      default-expand-all
      ref="tableRef"
      :data="tableData"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
    >
      <el-table-column prop="date" label="Date" sortable />
      <el-table-column prop="name" label="Name" sortable />
      <el-table-column prop="address" label="Address" sortable />
    </el-table>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
const tableRef = ref();
const expand = () => {
  console.log(1);
  tableRef.value.toggleRowExpansion({ id: 3 }, false);
  tableRef.value.setCurrentRow({ id: 2 });
};

interface User {
  id: number;
  date: string;
  name: string;
  address: string;
  hasChildren?: boolean;
  children?: User[];
}

const tableData: User[] = [
  {
    id: 1,
    date: "2016-05-02",
    name: "wangxiaohu",
    address: "No. 189, Grove St, Los Angeles"
  },
  {
    id: 2,
    date: "2016-05-04",
    name: "wangxiaohu",
    address: "No. 189, Grove St, Los Angeles"
  },
  {
    id: 3,
    date: "2016-05-01",
    name: "wangxiaohu",
    address: "No. 189, Grove St, Los Angeles",
    children: [
      {
        id: 31,
        date: "2016-05-01",
        name: "wangxiaohu",
        address: "No. 189, Grove St, Los Angeles",
        children: [
          {
            id: 44,
            date: "2004-04-04",
            name: "4444444444444",
            address: "No. 189, Grove St, Los Angeles"
          }
        ]
      },
      {
        id: 32,
        date: "2016-05-01",
        name: "wangxiaohu",
        address: "No. 189, Grove St, Los Angeles"
      }
    ]
  },
  {
    id: 4,
    date: "2016-05-03",
    name: "wangxiaohu",
    address: "No. 189, Grove St, Los Angeles"
  }
];
</script>
