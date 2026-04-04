<template>
  <div class="comment-page">
    <!-- 搜索栏 -->
    <el-card class="toolbar">
      <el-select
        v-model="searchForm.articleId"
        placeholder="选择文章"
        clearable
        @change="handleSearch"
      >
        <el-option
          v-for="option in articleOptions"
          :key="option.value"
          :label="option.label"
          :value="option.value"
        />
      </el-select>
      <el-select
        v-model="searchForm.isApproved"
        placeholder="审核状态"
        clearable
        @change="handleSearch"
      >
        <el-option label="已通过" :value="1" />
        <el-option label="待审核" :value="0" />
      </el-select>
      <el-button type="primary" @click="handleSearch">查询</el-button>
      <el-button
        plain
        type="success"
        :disabled="!selected.length"
        @click="batchApprove"
      >
        批量通过
      </el-button>
      <el-button
        plain
        type="danger"
        :disabled="!selected.length"
        @click="batchDelete"
      >
        批量删除
      </el-button>
    </el-card>

    <!-- 表格 -->
    <el-card v-loading="commentStore.loading">
      <el-table
        :data="commentStore.list"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="48" />
        <el-table-column prop="nickname" label="评论人" width="120" />
        <el-table-column prop="contentHtml" label="评论内容" min-width="300" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评论时间" width="160" />
        <el-table-column label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.isApproved ? 'success' : 'warning'">
              {{ row.isApproved ? '已通过' : '待审核' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link size="small" @click="handleReply(row)">
              回复
            </el-button>
            <el-divider direction="vertical" />
            <el-button link size="small" type="danger" @click="deleteOne(row)">
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
          :total="commentStore.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <!-- 回复对话框 -->
    <el-dialog v-model="replyDialogVisible" title="回复评论" width="600px">
      <el-form ref="replyFormRef" :model="replyForm" label-width="80px">
        <el-form-item label="回复内容">
          <el-input
            v-model="replyForm.content"
            type="textarea"
            :rows="4"
            placeholder="请输入回复内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReply">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useCommentStore } from '@/stores'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getArticlePage } from '@/api/article'

const commentStore = useCommentStore()

const searchForm = ref({ articleId: '', isApproved: '' })
const page = ref(1)
const size = ref(15)
const selected = ref([])
const articleOptions = ref([])
const replyDialogVisible = ref(false)
const replyFormRef = ref(null)
const replyForm = reactive({
  articleId: null,
  parentId: null,
  rootId: null,
  parentNickname: '',
  content: ''
})

// 加载文章列表到下拉框
const loadArticleOptions = async () => {
  try {
    const res = await getArticlePage({ page: 1, pageSize: 100 })
    console.log('=== 后端返回的完整响应 ===', res)
    console.log('=== res.code ===', res.code)
    console.log('=== res.data ===', res.data)
    console.log('=== res.data.records ===', res.data?.records)

    // 检查 records 是否为数组
    if (!Array.isArray(res.data?.records)) {
      console.error('records 不是数组，类型:', typeof res.data?.records)
      return
    }

    console.log('=== records 长度 ===', res.data.records.length)

    if (res.data.records.length > 0) {
      const first = res.data.records[0]
      console.log('=== 第一条记录所有字段 ===', Object.keys(first))
      console.log('=== 第一条记录 id 值 ===', first.id)
      console.log('=== 第一条记录 title 值 ===', first.title)
      console.log('=== 第一条记录完整数据 ===', first)
    }

    // 转换数据
    articleOptions.value = res.data.records.map(article => {
      const item = {
        label: article.title,
        value: article.id
      }
      console.log('map 处理 - 原始 article:', article, '=> 转换后:', item)
      return item
    })

    console.log('=== 最终 articleOptions ===', articleOptions.value)
  } catch (error) {
    console.error('加载文章列表失败', error)
  }
}

const handleSelectionChange = (rows) => {
  selected.value = rows
}

const load = () => {
  const params = {
    page: page.value,
    pageSize: size.value
  }
  // 只有当 articleId 和 isApproved 不为空时才传递
  if (searchForm.value.articleId) {
    params.articleId = searchForm.value.articleId
  }
  if (searchForm.value.isApproved !== '' && searchForm.value.isApproved !== null) {
    params.isApproved = searchForm.value.isApproved
  }
  commentStore.fetchList(params)
}

const handleSearch = () => {
  page.value = 1
  load()
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

const handleReply = (row) => {
  replyForm.articleId = row.articleId
  replyForm.parentId = row.id
  replyForm.rootId = row.rootId || row.id
  replyForm.parentNickname = row.nickname
  replyForm.content = ''
  replyDialogVisible.value = true
}

const submitReply = async () => {
  if (!replyForm.content) {
    ElMessage.warning('请输入回复内容')
    return
  }
  await commentStore.reply(replyForm)
  ElMessage.success('回复成功')
  replyDialogVisible.value = false
  load()
}

const batchApprove = async () => {
  await commentStore.approve(selected.value.map((r) => r.id))
  ElMessage.success('批量通过成功')
  load()
}

const batchDelete = async () => {
  await ElMessageBox.confirm('确认批量删除选中的评论？', '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await commentStore.remove(selected.value.map((r) => r.id))
  ElMessage.success('批量删除成功')
  load()
}

const deleteOne = async (row) => {
  await ElMessageBox.confirm('确认删除该评论？', '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await commentStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(() => {
  loadArticleOptions()
  load()
})
</script>

<style scoped lang="scss">
.comment-page {
  padding: 20px;

  .toolbar {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
    margin-bottom: 16px;
  }

  .pagination-wrap {
    display: flex;
    justify-content: flex-end;
    margin-top: 16px;
  }
}
</style>
