<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SidebarCard from '@/components/SidebarCard.vue'
import ArticleCard from '@/components/ArticleCard.vue'

const route = useRoute()
const tag = ref(null)
const articles = ref([])

const loadTag = async () => {
  // TODO: 调用 API 获取标签和文章
  tag.value = { id: route.params.id, name: 'Spring Boot', count: 10 }
  articles.value = [
    {
      id: 1,
      title: 'Spring Boot 3.0 新特性详解',
      summary: 'Spring Boot 3.0 带来了许多令人兴奋的新特性...',
      cover: 'https://picsum.photos/400/250?random=1',
      createTime: '2024-01-15 10:30:00',
      viewCount: 1234,
      commentCount: 23,
      categoryName: 'Java',
      top: true
    }
  ]
}

onMounted(() => {
  loadTag()
})
</script>

<template>
  <div class="tag-page">
    <div class="tag-layout">
      <div class="tag-main">
        <div v-if="tag" class="tag-header">
          <h1 class="tag-name">{{ tag.name }}</h1>
          <span class="tag-count">{{ tag.count }} 篇文章</span>
        </div>
        <div v-if="articles.length" class="article-list">
          <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
        </div>
        <div v-else class="empty-tip">该标签下暂无文章</div>
      </div>
      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.tag-page {
  width: 100%;
}

.tag-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.tag-main {
  flex: 1;
  min-width: 0;
}

.tag-header {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  padding: 32px;
  margin-bottom: 20px;
  text-align: center;
}

.tag-name {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  margin: 0 0 8px;
}

.tag-count {
  font-size: 14px;
  color: #909399;
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.empty-tip {
  padding: 60px 0;
  text-align: center;
  color: #909399;
  background: #fff;
  border-radius: 10px;
  border: 1px solid #ebeef5;
}

@media (max-width: 960px) {
  .tag-layout {
    flex-direction: column;
  }
}
</style>
