<script setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'
import dayjs from 'dayjs'

const props = defineProps({
  article: {
    type: Object,
    required: true
  }
})

const router = useRouter()

const formatDate = (date) => dayjs(date).format('YYYY-MM-DD')

const goToArticle = () => {
  router.push(`/article/${props.article.id}`)
}
</script>

<template>
  <article class="article-card" @click="goToArticle">
    <div class="article-cover">
      <img v-if="article.cover" :src="article.cover" :alt="article.title" class="cover-image" />
      <div v-else class="cover-placeholder">
        <svg viewBox="0 0 24 24" width="48" height="48" fill="none" stroke="currentColor" stroke-width="1.5">
          <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
          <circle cx="8.5" cy="8.5" r="1.5" />
          <polyline points="21 15 16 10 5 21" />
        </svg>
      </div>
      <div v-if="article.top" class="top-tag">置顶</div>
    </div>
    <div class="article-body">
      <h2 class="article-title">{{ article.title }}</h2>
      <p class="article-summary">{{ article.summary || '暂无摘要...' }}</p>
      <div class="article-meta">
        <span class="meta-item">
          <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2" />
            <circle cx="12" cy="7" r="4" />
          </svg>
          {{ article.author || '管理员' }}
        </span>
        <span class="meta-item">
          <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
            <line x1="16" y1="2" x2="16" y2="6" />
            <line x1="8" y1="2" x2="8" y2="6" />
            <line x1="3" y1="10" x2="21" y2="10" />
          </svg>
          {{ formatDate(article.createTime) }}
        </span>
        <span class="meta-item">
          <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
            <circle cx="12" cy="12" r="3" />
          </svg>
          {{ article.viewCount || 0 }}
        </span>
        <span class="meta-item">
          <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z" />
          </svg>
          {{ article.commentCount || 0 }}
        </span>
      </div>
      <div class="article-footer">
        <span class="category-tag">{{ article.categoryName || '未分类' }}</span>
      </div>
    </div>
  </article>
</template>

<style scoped lang="scss">
.article-card {
  display: flex;
  gap: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  border: 1px solid #ebeef5;
  margin-bottom: 16px;
  cursor: pointer;
  transition:
    transform 0.2s,
    box-shadow 0.2s,
    border-color 0.2s;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
    border-color: #dcdfe6;
  }
}

.article-cover {
  position: relative;
  flex-shrink: 0;
  width: 220px;
  height: 140px;
  border-radius: 8px;
  overflow: hidden;

  .cover-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s;
  }

  &:hover .cover-image {
    transform: scale(1.05);
  }

  .cover-placeholder {
    width: 100%;
    height: 100%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
  }

  .top-tag {
    position: absolute;
    top: 8px;
    left: 8px;
    padding: 3px 10px;
    background: #e6a23c;
    color: #fff;
    font-size: 12px;
    font-weight: 600;
    border-radius: 4px;
    box-shadow: 0 2px 8px rgba(230, 162, 60, 0.4);
  }
}

.article-body {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
}

.article-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 10px;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-summary {
  font-size: 14px;
  color: #909399;
  line-height: 1.7;
  margin: 0 0 12px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  flex: 1;
}

.article-meta {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  margin-bottom: 12px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #909399;

  svg {
    color: #c0c4cc;
  }
}

.article-footer {
  display: flex;
  align-items: center;
  gap: 8px;
}

.category-tag {
  display: inline-block;
  padding: 3px 10px;
  background: #f5f7fa;
  color: #606266;
  font-size: 12px;
  border-radius: 4px;
  font-weight: 500;
}

@media (max-width: 768px) {
  .article-card {
    flex-direction: column;
  }

  .article-cover {
    width: 100%;
    height: 180px;
  }
}

@media (max-width: 600px) {
  .article-card {
    padding: 16px;
  }

  .article-title {
    font-size: 16px;
  }

  .article-cover {
    height: 160px;
  }
}
</style>
