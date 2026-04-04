<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const hotArticles = ref([])
const categories = ref([])
const tags = ref([])
const recentComments = ref([])

const personalInfo = computed(() => ({
  name: 'FeiTwnd',
  avatar: 'https://picsum.photos/100/100?random=1',
  signature: '热爱编程，热爱生活',
  articleCount: 42,
  viewCount: 12345,
  github: 'https://github.com/FeiTwnd'
}))

const loadSidebarData = async () => {
  // TODO: 调用 API 获取数据
  hotArticles.value = [
    { id: 1, title: 'Spring Boot 3.0 新特性详解', viewCount: 1234 },
    { id: 2, title: 'Vue 3 Composition API 最佳实践', viewCount: 892 },
    { id: 3, title: 'MySQL 索引优化实战', viewCount: 756 }
  ]
  categories.value = [
    { id: 1, name: 'Java', count: 15 },
    { id: 2, name: '前端', count: 20 },
    { id: 3, name: '数据库', count: 8 },
    { id: 4, name: '随笔', count: 5 }
  ]
  tags.value = [
    { id: 1, name: 'Spring Boot', count: 10 },
    { id: 2, name: 'Vue', count: 8 },
    { id: 3, name: 'MySQL', count: 6 },
    { id: 4, name: 'Redis', count: 5 }
  ]
  recentComments.value = [
    { id: 1, nickname: '张三', content: '写得很好，学习了！', articleTitle: 'Spring Boot 3.0 新特性详解' },
    { id: 2, nickname: '李四', content: '感谢分享', articleTitle: 'Vue 3 Composition API 最佳实践' }
  ]
}

const goToArticle = (id) => {
  router.push(`/article/${id}`)
}

const goToCategory = (id) => {
  router.push({ path: '/blog', query: { category: id } })
}

const goToTag = (id) => {
  router.push(`/tag/${id}`)
}

onMounted(() => {
  loadSidebarData()
})
</script>

<template>
  <aside class="sidebar">
    <!-- 个人信息卡片 -->
    <div class="sidebar-card profile-card">
      <div class="profile-avatar">
        <img :src="personalInfo.avatar" alt="头像" />
      </div>
      <h3 class="profile-name">{{ personalInfo.name }}</h3>
      <p class="profile-signature">{{ personalInfo.signature }}</p>
      <div class="profile-stats">
        <div class="stat-item">
          <span class="stat-value">{{ personalInfo.articleCount }}</span>
          <span class="stat-label">文章</span>
        </div>
        <div class="stat-item">
          <span class="stat-value">{{ personalInfo.viewCount }}</span>
          <span class="stat-label">阅读</span>
        </div>
      </div>
      <a v-if="personalInfo.github" :href="personalInfo.github" target="_blank" class="github-link">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor">
          <path d="M12 .3a12 12 0 00-3.8 23.38c.6.12.83-.26.83-.57L9 21.07c-3.34.72-4.04-1.61-4.04-1.61-.55-1.39-1.34-1.76-1.34-1.76-1.08-.74.08-.73.08-.73 1.2.08 1.84 1.24 1.84 1.24 1.07 1.83 2.81 1.3 3.5 1 .1-.78.42-1.3.76-1.6-2.67-.3-5.47-1.33-5.47-5.93 0-1.31.47-2.38 1.24-3.22-.13-.3-.54-1.52.12-3.18 0 0 1-.33 3.3 1.23a11.5 11.5 0 016.02 0c2.28-1.56 3.29-1.23 3.29-1.23.66 1.66.25 2.88.12 3.18a4.65 4.65 0 011.23 3.22c0 4.61-2.81 5.63-5.48 5.93.43.37.81 1.1.81 2.22l-.01 3.29c0 .31.22.69.83.57A12 12 0 0012 .3" />
        </svg>
        GitHub
      </a>
    </div>

    <!-- 热门文章 -->
    <div class="sidebar-card">
      <h3 class="sidebar-title">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M12 20h9" />
          <path d="M16.5 3.5a2.121 2.121 0 013 3L7 19l-4 1 1-4L16.5 3.5z" />
        </svg>
        热门文章
      </h3>
      <ul class="article-list">
        <li v-for="article in hotArticles" :key="article.id" class="article-item">
          <span class="article-rank">{{ hotArticles.indexOf(article) + 1 }}</span>
          <span class="article-title-link" @click="goToArticle(article.id)">{{ article.title }}</span>
          <span class="article-views">{{ article.viewCount }}</span>
        </li>
      </ul>
    </div>

    <!-- 分类目录 -->
    <div class="sidebar-card">
      <h3 class="sidebar-title">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M22 19a2 2 0 01-2 2H4a2 2 0 01-2-2V5a2 2 0 012-2h5l2 3h9a2 2 0 012 2z" />
        </svg>
        分类目录
      </h3>
      <div class="category-list">
        <span
          v-for="cat in categories"
          :key="cat.id"
          class="category-tag"
          @click="goToCategory(cat.id)"
        >
          {{ cat.name }}
          <span class="category-count">{{ cat.count }}</span>
        </span>
      </div>
    </div>

    <!-- 标签云 -->
    <div class="sidebar-card">
      <h3 class="sidebar-title">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20.59 13.41l-7.17 7.17a2 2 0 01-2.83 0L2 12V2h10l8.59 8.59a2 2 0 010 2.82z" />
          <line x1="7" y1="7" x2="7.01" y2="7" />
        </svg>
        标签云
      </h3>
      <div class="tag-cloud">
        <span
          v-for="tag in tags"
          :key="tag.id"
          class="tag-item"
          @click="goToTag(tag.id)"
        >
          {{ tag.name }}
          <small>{{ tag.count }}</small>
        </span>
      </div>
    </div>

    <!-- 最新评论 -->
    <div class="sidebar-card">
      <h3 class="sidebar-title">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z" />
        </svg>
        最新评论
      </h3>
      <ul class="comment-list">
        <li v-for="comment in recentComments" :key="comment.id" class="comment-item">
          <div class="comment-author">{{ comment.nickname }}</div>
          <div class="comment-content">{{ comment.content }}</div>
          <div class="comment-article">@ {{ comment.articleTitle }}</div>
        </li>
      </ul>
    </div>
  </aside>
</template>

<style scoped lang="scss">
.sidebar {
  width: 280px;
  flex-shrink: 0;
}

.sidebar-card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  padding: 20px;
  margin-bottom: 20px;
}

.sidebar-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #ebeef5;
}

/* 个人信息卡片 */
.profile-card {
  text-align: center;

  .profile-avatar {
    width: 80px;
    height: 80px;
    margin: 0 auto 12px;
    border-radius: 50%;
    overflow: hidden;
    border: 3px solid #409eff;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .profile-name {
    font-size: 18px;
    font-weight: 600;
    color: #303133;
    margin: 0 0 8px;
  }

  .profile-signature {
    font-size: 13px;
    color: #909399;
    margin: 0 0 16px;
    font-style: italic;
  }

  .profile-stats {
    display: flex;
    justify-content: center;
    gap: 24px;
    padding: 12px 0;
    border-top: 1px solid #f5f7fa;
    border-bottom: 1px solid #f5f7fa;
    margin-bottom: 12px;

    .stat-item {
      text-align: center;

      .stat-value {
        display: block;
        font-size: 20px;
        font-weight: 600;
        color: #409eff;
      }

      .stat-label {
        font-size: 12px;
        color: #909399;
      }
    }
  }

  .github-link {
    display: inline-flex;
    align-items: center;
    gap: 6px;
    padding: 8px 16px;
    background: #24292e;
    color: #fff;
    border-radius: 6px;
    text-decoration: none;
    font-size: 13px;
    transition: opacity 0.2s;

    &:hover {
      opacity: 0.9;
    }
  }
}

/* 文章列表 */
.article-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.article-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px solid #f5f7fa;

  &:last-child {
    border-bottom: none;
  }

  .article-rank {
    width: 20px;
    height: 20px;
    background: #f5f7fa;
    color: #909399;
    font-size: 12px;
    font-weight: 600;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }

  .article-title-link {
    flex: 1;
    font-size: 13px;
    color: #606266;
    cursor: pointer;
    transition: color 0.2s;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;

    &:hover {
      color: #409eff;
    }
  }

  .article-views {
    font-size: 12px;
    color: #c0c4cc;
    white-space: nowrap;
  }
}

/* 分类列表 */
.category-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.category-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 6px 12px;
  background: #f5f7fa;
  color: #606266;
  font-size: 13px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;

  &:hover {
    background: #409eff;
    color: #fff;
  }

  .category-count {
    font-size: 11px;
    opacity: 0.8;
  }
}

/* 标签云 */
.tag-cloud {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag-item {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 5px 10px;
  background: #f5f7fa;
  color: #606266;
  font-size: 12px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;

  small {
    font-size: 10px;
    opacity: 0.7;
  }

  &:hover {
    background: #67c23a;
    color: #fff;
  }
}

/* 评论列表 */
.comment-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.comment-item {
  padding: 12px 0;
  border-bottom: 1px solid #f5f7fa;

  &:last-child {
    border-bottom: none;
  }

  .comment-author {
    font-size: 13px;
    color: #303133;
    font-weight: 500;
    margin-bottom: 6px;
  }

  .comment-content {
    font-size: 13px;
    color: #909399;
    margin-bottom: 6px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  .comment-article {
    font-size: 12px;
    color: #c0c4cc;
    font-style: italic;
  }
}

@media (max-width: 960px) {
  .sidebar {
    width: 100%;
  }
}
</style>
