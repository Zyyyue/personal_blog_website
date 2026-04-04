<template>
  <div class="article-page">
    <!-- 搜索栏 -->
    <el-card class="toolbar">
      <div class="toolbar-left">
        <el-input
          v-model="searchForm.title"
          placeholder="搜索文章标题"
          clearable
          class="search-input"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-select
          v-model="searchForm.categoryId"
          placeholder="全部分类"
          clearable
          class="select-sm"
        >
          <el-option
            v-for="c in categoryStore.list"
            :key="c.id"
            :label="c.name"
            :value="c.id"
          />
        </el-select>
        <el-select
          v-model="searchForm.isPublished"
          placeholder="全部状态"
          clearable
          class="select-sm"
        >
          <el-option label="已发布" :value="1" />
          <el-option label="草稿" :value="0" />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon><Search /></el-icon> 查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
      </div>
      <div class="toolbar-right">
        <el-button
          plain
          type="danger"
          :disabled="!selected.length"
          @click="batchDelete"
        >
          <el-icon><Delete /></el-icon> 批量删除
        </el-button>
        <el-button type="primary" @click="toCreate">
          <el-icon><Plus /></el-icon> 新建文章
        </el-button>
      </div>
    </el-card>

    <!-- 表格 -->
    <el-card class="table-wrap" v-loading="articleStore.loading">
      <el-table
        :data="articleStore.list"
        border
        stripe
        row-key="id"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="48" />
        <el-table-column
          prop="title"
          label="标题"
          min-width="220"
          show-overflow-tooltip
        />
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column label="状态" width="90" align="center">
          <template #default="{ row }">
            <el-tag :type="row.isPublished ? 'success' : 'info'">
              {{ row.isPublished ? '已发布' : '草稿' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="置顶" width="75" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.isTop" type="warning" size="small">置顶</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="viewCount" label="阅读" width="75" align="center" />
        <el-table-column
          prop="commentCount"
          label="评论"
          width="75"
          align="center"
        />
        <el-table-column label="发布时间" width="160" align="center">
          <template #default="{ row }">{{
            fmtDate(row.publishTime || row.createTime)
          }}</template>
        </el-table-column>
        <el-table-column label="操作" width="280" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link size="small" @click="toEdit(row.id)">
              编辑
            </el-button>
            <el-divider direction="vertical" />
            <el-button link size="small" @click="togglePublish(row)">
              {{ row.isPublished ? '撤回' : '发布' }}
            </el-button>
            <el-divider direction="vertical" />
            <el-button link size="small" @click="toggleTop(row)">
              {{ row.isTop ? '取消置顶' : '置顶' }}
            </el-button>
            <el-divider direction="vertical" />
            <el-button link size="small" type="danger" @click="deleteOne(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrap">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :page-sizes="[10, 15, 20, 50]"
          :total="articleStore.total"
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
import { useRouter } from 'vue-router'
import { useArticleStore, useCategoryStore } from '@/stores'
import { Search, Plus, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'

const router = useRouter()
const articleStore = useArticleStore()
const categoryStore = useCategoryStore()

const searchForm = ref({ title: '', categoryId: '', isPublished: '' })
const page = ref(1)
const size = ref(15)
const selected = ref([])

const handleSelectionChange = (rows) => {
  selected.value = rows
}

const load = () => {
  articleStore.fetchList({
    page: page.value,
    pageSize: size.value,
    ...searchForm.value
  })
  categoryStore.fetchList()
}

const handleSearch = () => {
  page.value = 1
  load()
}

const handleReset = () => {
  searchForm.value = { title: '', categoryId: '', isPublished: '' }
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

const toEdit = (id) => {
  router.push(`/article/edit/${id}`)
}

const toCreate = () => {
  router.push('/article/edit')
}

const togglePublish = async (row) => {
  await articleStore.toggleArticlePublish(row.id, row.isPublished ? 0 : 1)
  ElMessage.success('操作成功')
  load()
}

const toggleTop = async (row) => {
  await articleStore.toggleArticleTop(row.id, row.isTop ? 0 : 1)
  ElMessage.success('操作成功')
  load()
}

const deleteOne = async (row) => {
  await ElMessageBox.confirm(`确认删除文章「${row.title}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await articleStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

const batchDelete = async () => {
  if (!selected.value.length) return ElMessage.warning('请先选择文章')
  await ElMessageBox.confirm(
    `确认删除选中的 ${selected.value.length} 篇文章？`,
    '警告',
    {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
  await articleStore.remove(selected.value.map((r) => r.id))
  ElMessage.success('批量删除成功')
  load()
}

const fmtDate = (d) => (d ? dayjs(d).format('YYYY-MM-DD HH:mm') : '-')

onMounted(load)
</script>

<style scoped lang="scss">
.article-page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  .toolbar-left {
    display: flex;
    align-items: center;
    gap: 8px;
    flex-wrap: wrap;
  }

  .toolbar-right {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-top: 12px;

    @media (min-width: 768px) {
      margin-top: 0;
    }
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
</style>
