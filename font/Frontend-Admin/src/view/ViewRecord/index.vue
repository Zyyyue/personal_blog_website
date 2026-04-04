<template>
  <div class="view-record-page">
    <el-card class="toolbar">
      <div class="toolbar-left">
        <el-input
          v-model="searchForm.ip"
          placeholder="搜索 IP 地址"
          clearable
          class="search-input"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-select
          v-model="searchForm.type"
          placeholder="全部类型"
          clearable
          class="select-sm"
        >
          <el-option label="文章" :value="1" />
          <el-option label="首页" :value="2" />
          <el-option label="关于" :value="3" />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon><Search /></el-icon> 查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
      </div>
    </el-card>

    <el-card class="table-wrap" v-loading="viewRecordStore.loading">
      <el-table :data="viewRecordStore.list" border stripe>
        <el-table-column prop="ip" label="IP 地址" width="140" />
        <el-table-column prop="ipInfo" label="IP 归属地" min-width="150" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.type === 1">文章</el-tag>
            <el-tag v-else-if="row.type === 2" type="success">首页</el-tag>
            <el-tag v-else-if="row.type === 3" type="warning">关于</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="articleId" label="文章 ID" width="80" />
        <el-table-column prop="visitTime" label="访问时间" width="160">
          <template #default="{ row }">{{ fmtDate(row.visitTime) }}</template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrap">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :page-sizes="[10, 15, 20, 50]"
          :total="viewRecordStore.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useViewRecordStore } from '@/stores'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'

const viewRecordStore = useViewRecordStore()

const searchForm = ref({ ip: '', type: '' })
const page = ref(1)
const size = ref(15)

const handleSearch = () => {
  page.value = 1
  load()
}

const handleReset = () => {
  searchForm.value = { ip: '', type: '' }
  handleSearch()
}

const handlePageChange = (p) => {
  page.value = p
  load()
}

const handleSizeChange = (s) => {
  size.value = s
  page.value = 1
  load()
}

const load = () => {
  viewRecordStore.fetchList({
    page: page.value,
    pageSize: size.value,
    ...searchForm.value
  })
}

const fmtDate = (d) => (d ? dayjs(d).format('YYYY-MM-DD HH:mm') : '-')

onMounted(load)
</script>

<style scoped lang="scss">
.view-record-page {
  display: flex;
  flex-direction: column;
  gap: 16px;

  .toolbar {
    .toolbar-left {
      display: flex;
      align-items: center;
      gap: 8px;
      flex-wrap: wrap;
    }

    .search-input {
      width: 200px;
    }

    .select-sm {
      width: 120px;
    }
  }

  .table-wrap {
    .pagination-wrap {
      display: flex;
      justify-content: flex-end;
      margin-top: 16px;
    }
  }
}
</style>
