<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import ArticleCard from '@/components/ArticleCard.vue'
import SidebarCard from '@/components/SidebarCard.vue'

const route = useRoute()

const articles = ref([])
const total = ref(0)
const page = ref(1)
const pageSize = 10
const loading = ref(false)
const searchKeyword = ref('')

// 分类筛选
const selectedCategory = ref('')
const categories = ref([])

// 标签筛选
const selectedTag = ref('')
const tags = ref([])

const loadFilters = async () => {
  // TODO: 调用 API 获取分类和标签列表
  categories.value = [
    { id: 1, name: 'Java', count: 15 },
    { id: 2, name: '前端', count: 20 },
    { id: 3, name: '数据库', count: 8 },
    { id: 4, name: '随笔', count: 5 }
  ]
  tags.value = [
    { id: 1, name: 'Spring Boot', count: 10 },
    { id: 2, name: 'Vue', count: 8 },
    { id: 3, name: 'MySQL', count: 6 }
  ]
}

const loadArticles = async () => {
  loading.value = true
  try {
    // TODO: 调用 API 获取文章列表
    // const res = await getArticlePage(page.value, pageSize, {
    //   categoryId: selectedCategory.value,
    //   tagId: selectedTag.value,
    //   keyword: searchKeyword.value
    // })
    // articles.value = res.data?.records || []
    // total.value = res.data?.total || 0

    // 模拟数据
    articles.value = [
      {
        id: 1,
        title: 'Spring Boot 3.0 新特性详解',
        summary: 'Spring Boot 3.0 带来了许多令人兴奋的新特性，包括对 Java 17 的最低版本要求、原生镜像支持、Observation API 等...',
        cover: 'https://picsum.photos/400/250?random=1',
        createTime: '2024-01-15 10:30:00',
        viewCount: 1234,
        commentCount: 23,
        categoryName: 'Java',
        top: true
      },
      {
        id: 2,
        title: 'Vue 3 Composition API 最佳实践',
        summary: 'Composition API 是 Vue 3 的核心特性之一，它提供了更好的代码组织方式和类型推断能力...',
        cover: 'https://picsum.photos/400/250?random=2',
        createTime: '2024-01-14 15:20:00',
        viewCount: 892,
        commentCount: 15,
        categoryName: '前端',
        top: false
      },
      {
        id: 3,
        title: 'MySQL 索引优化实战',
        summary: '索引是数据库优化中最重要的手段之一。本文将通过实际案例，讲解如何正确使用索引来提升查询性能...',
        cover: 'https://picsum.photos/400/250?random=3',
        createTime: '2024-01-13 09:15:00',
        viewCount: 756,
        commentCount: 12,
        categoryName: '数据库',
        top: false
      }
    ]
    total.value = 3
  } catch (error) {
    console.error(error)
    articles.value = []
  } finally {
    loading.value = false
  }
}

const handlePageChange = (p) => {
  page.value = p
  loadArticles()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const handleCategoryChange = (id) => {
  selectedCategory.value = id
  page.value = 1
  loadArticles()
}

const handleTagChange = (id) => {
  selectedTag.value = id
  page.value = 1
  loadArticles()
}

watch(
  () => route.query.search,
  (kw) => {
    searchKeyword.value = kw || ''
    page.value = 1
    loadArticles()
  }
)

onMounted(() => {
  searchKeyword.value = route.query.search || ''
  loadFilters()
  loadArticles()
})
</script>

<template>
  <div class="blog-page">
    <div class="blog-content">
      <!-- 左侧：文章列表 -->
      <div class="article-col">
        <!-- 筛选栏 -->
        <div class="filter-bar">
          <div class="filter-group">
            <span class="filter-label">分类：</span>
            <el-tag
              v-for="c in categories"
              :key="c.id"
              :type="selectedCategory === c.id ? 'primary' : 'info'"
              :effect="selectedCategory === c.id ? 'dark' : 'plain'"
              class="filter-tag"
              @click="handleCategoryChange(c.id)"
            >
              {{ c.name }}
            </el-tag>
            <el-tag
              v-if="selectedCategory"
              type="info"
              class="filter-tag clear-btn"
              @click="handleCategoryChange('')"
            >
              取消
            </el-tag>
          </div>
          <div class="filter-group">
            <span class="filter-label">标签：</span>
            <el-tag
              v-for="t in tags"
              :key="t.id"
              :type="selectedTag === t.id ? 'primary' : 'info'"
              :effect="selectedTag === t.id ? 'dark' : 'plain'"
              class="filter-tag"
              @click="handleTagChange(t.id)"
            >
              {{ t.name }}
            </el-tag>
            <el-tag
              v-if="selectedTag"
              type="info"
              class="filter-tag clear-btn"
              @click="handleTagChange('')"
            >
              取消
            </el-tag>
          </div>
        </div>

        <!-- 文章列表 -->
        <div v-if="loading" class="loading-placeholder">
          <div v-for="i in 4" :key="i" class="skeleton-card">
            <div class="skeleton-cover" />
            <div class="skeleton-body">
              <div class="skeleton-line w60" />
              <div class="skeleton-line w90" />
              <div class="skeleton-line w40" />
            </div>
          </div>
        </div>

        <template v-else-if="articles.length">
          <ArticleCard v-for="a in articles" :key="a.id" :article="a" />
          <div v-if="total > pageSize" class="pagination-wrap">
            <el-pagination
              :current-page="page"
              :page-size="pageSize"
              :total="total"
              layout="prev, pager, next"
              background
              @current-change="handlePageChange"
            />
          </div>
        </template>

        <div v-else class="empty-tip">暂无文章</div>
      </div>

      <!-- 右侧：侧边栏 -->
      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.blog-page {
  width: 100%;
}

.blog-content {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.article-col {
  flex: 1;
  min-width: 0;
}

/* 筛选栏 */
.filter-bar {
  background: #fff;
  border-radius: 8px;
  border: 1px solid #ebeef5;
  padding: 16px;
  margin-bottom: 16px;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;

  &:last-child {
    margin-bottom: 0;
  }
}

.filter-label {
  font-size: 13px;
  color: #909399;
  font-weight: 500;
  white-space: nowrap;
}

.filter-tag {
  cursor: pointer;
  user-select: none;

  &.clear-btn {
    opacity: 0.7;
  }
}

/* 骨架屏 */
.loading-placeholder {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.skeleton-card {
  display: flex;
  gap: 16px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  border: 1px solid #ebeef5;
}

.skeleton-cover {
  width: 200px;
  height: 130px;
  background: #ebeef5;
  border-radius: 6px;
  flex-shrink: 0;
}

.skeleton-body {
  flex: 1;
}

.skeleton-line {
  height: 14px;
  background: #ebeef5;
  border-radius: 4px;
  margin-bottom: 10px;
}

.w60 {
  width: 60%;
}

.w90 {
  width: 90%;
}

.w40 {
  width: 40%;
}

/* 分页 */
.pagination-wrap {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.empty-tip {
  padding: 60px 0;
  text-align: center;
  color: #909399;
  font-size: 14px;
  background: #fff;
  border-radius: 8px;
  border: 1px solid #ebeef5;
}

@media (max-width: 960px) {
  .blog-content {
    flex-direction: column;
  }
}

@media (max-width: 600px) {
  .skeleton-card {
    flex-direction: column;
  }

  .skeleton-cover {
    width: 100%;
    height: 160px;
  }

  .filter-group {
    flex-wrap: wrap;
  }
}
</style>
