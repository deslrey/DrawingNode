<template>
  <el-container style="height: 500px; border: 1px solid #eee">
    <el-container>
      <el-main>
        <el-table :data="userList">
          <el-table-column prop="id" label="id" width="140">
          </el-table-column>
          <el-table-column prop="nickName" label="姓名" width="120">
          </el-table-column>
          <el-table-column prop="passWord" label="密码">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import index from '@/api/index'

export default {
  name: 'Dashboard',
  data() {
    return {
      userList: []
    }
  },

  mounted() {
    this.list()
  },
  methods: {
    list() {
      index.getAllUser().then(response => {
        this.userList = response.data
        console.log(this.userList);
      })
    }
  },

}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
