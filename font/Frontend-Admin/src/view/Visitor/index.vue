<template>
  <div class="visitor-page">
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
          v-model="searchForm.isBlocked"
          placeholder="全部状态"
          clearable
          class="select-sm"
        >
          <el-option label="正常" :value="0" />
          <el-option label="已封禁" :value="1" />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon><Search /></el-icon> 查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
      </div>
    </el-card>

    <el-card class="table-wrap" v-loading="visitorStore.loading">
      <el-table :data="visitorStore.list" border stripe>
        <el-table-column prop="ip" label="IP 地址" width="140" />
        <el-table-column prop="ipInfo" label="IP 归属地" min-width="150" />
        <el-table-column prop="visitTime" label="访问时间" width="160">
          <template #default="{ row }">{{ fmtDate(row.visitTime) }}</template>
        </el-table-column>
        <el-table-column prop="visitPage" label="访问页面" min-width="200" show-overflow-tooltip />
        <el-table-column label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.isBlocked ? 'danger' : 'success'">
              {{ row.isBlocked ? '已封禁' : '正常' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template #default="{ row }">
            <el-button
              link
              size="small"
              :type="row.isBlocked ? 'success' : 'warning'"
              @click="toggleBlock(row)"
            >
              {{ row.isBlocked ? '解封' : '封禁' }}
            </el-button>
            <el-divider direction="vertical" />
            <el-button link size="small" type="danger" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrap">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :page-sizes="[10, 15, 20, 50]"
          :total="visitorStore.total"
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
import { useVisitorStore } from '@/stores'
import { Search } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'

const visitorStore = useVisitorStore()

const searchForm = ref({ ip: '', isBlocked: '' })
const page = ref(1)
const size = ref(15)

const handleSearch = () => {
  page.value = 1
  load()
}

const handleReset = () => {
  searchForm.value = { ip: '', isBlocked: '' }
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
  visitorStore.fetchList({
    page: page.value,
    pageSize: size.value,
    ...searchForm.value
  })
}

const toggleBlock = async (row) => {
  const action = row.isBlocked ? '解封' : '封禁'
  await ElMessageBox.confirm(`确认${action}该访客？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await visitorStore.update({ id: row.id, isBlocked: row.isBlocked ? 0 : 1 })
  ElMessage.success('操作成功')
  load()
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除该访客记录？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await visitorStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

const fmtDate = (d) => (d ? dayjs(d).format('YYYY-MM-DD HH:mm') : '-')

onMounted(load)
</script>

<style scoped lang="scss">
.visitor-page {
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
