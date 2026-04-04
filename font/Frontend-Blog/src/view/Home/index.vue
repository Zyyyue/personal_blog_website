<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import ArticleCard from '@/components/ArticleCard.vue'
import SidebarCard from '@/components/SidebarCard.vue'

const route = useRoute()
const router = useRouter()

const articles = ref([])
const total = ref(0)
const page = ref(1)
const pageSize = 10
const loading = ref(false)
const searchKeyword = ref('')

const loadArticles = async () => {
  loading.value = true
  try {
    // TODO: 调用 API 获取文章列表
    // if (searchKeyword.value) {
    //   const res = await searchArticles(searchKeyword.value, page.value, pageSize)
    // } else {
    //   const res = await getArticlePage(page.value, pageSize)
    // }
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
      }
    ]
    total.value = 2
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
  loadArticles()
})
</script>

<template>
  <div class="home-page">
    <div class="home-content">
      <!-- 左侧：文章列表 -->
      <div class="article-col">
        <div v-if="searchKeyword" class="search-result-tip">
          <span>搜索：<strong>{{ searchKeyword }}</strong></span>
          <span class="search-count">{{ total }} 篇结果</span>
          <a class="clear-search" @click="router.push('/')">×</a>
        </div>

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
.home-page {
  width: 100%;
}

.home-content {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.article-col {
  flex: 1;
  min-width: 0;
}

/* 搜索提示 */
.search-result-tip {
  padding: 12px 16px;
  font-size: 14px;
  color: #606266;
  background: #fff;
  border-radius: 8px;
  border: 1px solid #ebeef5;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 8px;

  .search-count {
    color: #909399;
    font-size: 13px;
  }

  .clear-search {
    color: #303133;
    cursor: pointer;
    font-weight: 600;
    margin-left: auto;
    font-size: 18px;
    line-height: 1;
  }
}

/* 骨架屏 */
.skeleton-card {
  display: flex;
  gap: 16px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  margin-bottom: 16px;
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
  .home-content {
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
}
</style>
