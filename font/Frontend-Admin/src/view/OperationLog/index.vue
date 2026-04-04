<template>
  <div class="operation-log-page">
    <el-card class="toolbar">
      <div class="toolbar-left">
        <el-input
          v-model="searchForm.operator"
          placeholder="搜索操作人"
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
          <el-option label="新增" :value="1" />
          <el-option label="修改" :value="2" />
          <el-option label="删除" :value="3" />
          <el-option label="查询" :value="4" />
        </el-select>
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
          class="date-range"
          @change="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">
          <el-icon><Search /></el-icon> 查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
      </div>
    </el-card>

    <el-card class="table-wrap" v-loading="operationLogStore.loading">
      <el-table :data="operationLogStore.list" border stripe>
        <el-table-column prop="operator" label="操作人" width="120" />
        <el-table-column prop="operationIp" label="操作 IP" width="140" />
        <el-table-column prop="operationTime" label="操作时间" width="160">
          <template #default="{ row }">{{ fmtDate(row.operationTime) }}</template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.type === 1">新增</el-tag>
            <el-tag v-else-if="row.type === 2" type="warning">修改</el-tag>
            <el-tag v-else-if="row.type === 3" type="danger">删除</el-tag>
            <el-tag v-else-if="row.type === 4" type="info">查询</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="module" label="模块" min-width="120" />
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
      </el-table>

      <div class="pagination-wrap">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :page-sizes="[10, 15, 20, 50]"
          :total="operationLogStore.total"
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
import { useOperationLogStore } from '@/stores'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'

const operationLogStore = useOperationLogStore()

const searchForm = ref({ operator: '', type: '' })
const dateRange = ref([])
const page = ref(1)
const size = ref(15)

const handleSearch = () => {
  page.value = 1
  load()
}

const handleReset = () => {
  searchForm.value = { operator: '', type: '' }
  dateRange.value = []
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
  const params = {
    page: page.value,
    pageSize: size.value,
    ...searchForm.value
  }
  if (dateRange.value && dateRange.value.length === 2) {
    params.startTime = dateRange.value[0]
    params.endTime = dateRange.value[1]
  }
  operationLogStore.fetchList(params)
}

const fmtDate = (d) => (d ? dayjs(d).format('YYYY-MM-DD HH:mm') : '-')

onMounted(load)
</script>

<style scoped lang="scss">
.operation-log-page {
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
      width: 150px;
    }

    .select-sm {
      width: 120px;
    }

    .date-range {
      width: 240px;
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
