<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SidebarCard from '@/components/SidebarCard.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const article = ref(null)
const loading = ref(false)

const commentForm = ref({
  nickname: '',
  email: '',
  content: ''
})

const submitComment = async () => {
  if (!commentForm.value.content) {
    ElMessage.warning('请输入评论内容')
    return
  }
  // TODO: 调用 API 提交评论
  ElMessage.success('评论成功，等待审核')
  commentForm.value = { nickname: '', email: '', content: '' }
}

const loadArticle = async () => {
  loading.value = true
  try {
    // TODO: 调用 API 获取文章详情
    article.value = {
      id: route.params.id,
      title: 'Spring Boot 3.0 新特性详解',
      content: `
        <h2>前言</h2>
        <p>Spring Boot 3.0 带来了许多令人兴奋的新特性。本文将详细介绍这些新特性。</p>
        <h2>1. Java 17 最低版本要求</h2>
        <p>Spring Boot 3.0 最低要求 Java 17 版本，这是一个重要的里程碑。</p>
        <h2>2. 原生镜像支持</h2>
        <p>通过 GraalVM 原生镜像支持，可以显著提升启动速度和降低内存占用。</p>
        <h2>3. Observation API</h2>
        <p>新的 Observation API 提供了统一的观测框架。</p>
      `,
      createTime: '2024-01-15 10:30:00',
      updateTime: '2024-01-15 12:00:00',
      viewCount: 1234,
      commentCount: 23,
      categoryName: 'Java',
      tags: ['Spring Boot', 'Java', '后端'],
      author: '管理员',
      cover: 'https://picsum.photos/800/400?random=1'
    }
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadArticle()
})
</script>

<template>
  <div class="article-detail-page">
    <div class="article-layout">
      <div class="article-main">
        <div v-if="loading" class="loading-placeholder">
          <el-skeleton :rows="10" animated />
        </div>
        <article v-else-if="article" class="article-content">
          <header class="article-header">
            <h1 class="article-title">{{ article.title }}</h1>
            <div class="article-meta">
              <span class="meta-item">
                <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10" />
                  <polyline points="12 6 12 12 16 14" />
                </svg>
                {{ article.createTime }}
              </span>
              <span class="meta-item">
                <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
                  <circle cx="12" cy="12" r="3" />
                </svg>
                {{ article.viewCount }}
              </span>
              <span class="meta-item">
                <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z" />
                </svg>
                {{ article.commentCount }}
              </span>
            </div>
            <div class="article-tags">
              <span v-for="tag in article.tags" :key="tag" class="tag-item">{{ tag }}</span>
            </div>
          </header>
          <div class="article-body" v-html="article.content" />
        </article>
        <div v-else class="empty-tip">文章不存在</div>

        <!-- 评论区 -->
        <div class="comment-section">
          <h3 class="section-title">发表评论</h3>
          <div class="comment-form">
            <el-input
              v-model="commentForm.nickname"
              placeholder="昵称"
              style="margin-bottom: 12px"
            />
            <el-input
              v-model="commentForm.email"
              placeholder="邮箱（选填）"
              style="margin-bottom: 12px"
            />
            <el-input
              v-model="commentForm.content"
              type="textarea"
              :rows="4"
              placeholder="请输入评论内容"
              style="margin-bottom: 12px"
            />
            <el-button type="primary" @click="submitComment">提交评论</el-button>
          </div>
        </div>
      </div>

      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.article-detail-page {
  width: 100%;
}

.article-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.article-main {
  flex: 1;
  min-width: 0;
}

.loading-placeholder {
  background: #fff;
  padding: 24px;
  border-radius: 10px;
  border: 1px solid #ebeef5;
}

.article-content {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  overflow: hidden;
}

.article-header {
  padding: 32px;
  border-bottom: 1px solid #ebeef5;
}

.article-title {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  margin: 0 0 16px;
  line-height: 1.4;
}

.article-meta {
  display: flex;
  gap: 20px;
  margin-bottom: 16px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #909399;

  svg {
    color: #c0c4cc;
  }
}

.article-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.tag-item {
  padding: 4px 12px;
  background: #f5f7fa;
  color: #606266;
  font-size: 12px;
  border-radius: 4px;
  font-weight: 500;
}

.article-body {
  padding: 32px;
  font-size: 16px;
  line-height: 1.8;
  color: #303133;

  :deep(h2) {
    font-size: 20px;
    font-weight: 600;
    margin: 32px 0 16px;
    color: #303133;
  }

  :deep(p) {
    margin: 16px 0;
  }

  :deep(a) {
    color: #409eff;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }

  :deep(code) {
    background: #f5f7fa;
    padding: 2px 6px;
    border-radius: 4px;
    font-family: 'Fira Code', monospace;
    font-size: 14px;
  }

  :deep(pre) {
    background: #282c34;
    color: #abb2bf;
    padding: 16px;
    border-radius: 8px;
    overflow-x: auto;
    margin: 16px 0;

    code {
      background: transparent;
      padding: 0;
      color: inherit;
    }
  }
}

/* 评论区 */
.comment-section {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  padding: 24px;
  margin-top: 20px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 20px;
}

.empty-tip {
  padding: 60px 0;
  text-align: center;
  color: #909399;
}

@media (max-width: 960px) {
  .article-layout {
    flex-direction: column;
  }

  .article-title {
    font-size: 22px;
  }

  .article-body {
    padding: 20px;
    font-size: 15px;
  }
}
</style>
