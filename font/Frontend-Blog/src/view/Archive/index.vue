<script setup>
import { ref, onMounted } from 'vue'
import SidebarCard from '@/components/SidebarCard.vue'

const archives = ref([])

const loadArchives = async () => {
  // TODO: 调用 API 获取归档数据
  archives.value = [
    {
      year: '2024',
      months: [
        {
          month: '01',
          articles: [
            { id: 1, title: 'Spring Boot 3.0 新特性详解', date: '01-15', viewCount: 1234 },
            { id: 2, title: 'Vue 3 Composition API 最佳实践', date: '01-14', viewCount: 892 }
          ]
        }
      ]
    },
    {
      year: '2023',
      months: [
        {
          month: '12',
          articles: [
            { id: 3, title: 'MySQL 索引优化实战', date: '12-20', viewCount: 756 }
          ]
        }
      ]
    }
  ]
}

onMounted(() => {
  loadArchives()
})
</script>

<template>
  <div class="archive-page">
    <div class="archive-layout">
      <div class="archive-main">
        <h1 class="page-title">文章归档</h1>
        <div v-if="archives.length" class="archive-list">
          <div v-for="year in archives" :key="year.year" class="archive-year">
            <h2 class="year-title">{{ year.year }}</h2>
            <div v-for="month in year.months" :key="month.month" class="archive-month">
              <h3 class="month-title">{{ month.month }}月</h3>
              <ul class="article-list">
                <li v-for="article in month.articles" :key="article.id" class="article-item">
                  <router-link :to="`/article/${article.id}`" class="article-link">{{ article.title }}</router-link>
                  <span class="article-date">{{ article.date }}</span>
                  <span class="article-views">{{ article.viewCount }}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div v-else class="empty-tip">暂无归档数据</div>
      </div>
      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.archive-page {
  width: 100%;
}

.archive-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.archive-main {
  flex: 1;
  min-width: 0;
}

.page-title {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  margin: 0 0 24px;
  padding-bottom: 16px;
  border-bottom: 2px solid #409eff;
}

.archive-list {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  padding: 24px;
}

.archive-year {
  margin-bottom: 32px;

  &:last-child {
    margin-bottom: 0;
  }
}

.year-title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 16px;
  padding-left: 12px;
  border-left: 4px solid #409eff;
}

.archive-month {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.month-title {
  font-size: 16px;
  font-weight: 600;
  color: #606266;
  margin: 0 0 12px;
}

.article-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.article-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 0;
  border-bottom: 1px solid #f5f7fa;

  &:last-child {
    border-bottom: none;
  }

  .article-link {
    flex: 1;
    color: #606266;
    text-decoration: none;
    font-size: 14px;
    transition: color 0.2s;

    &:hover {
      color: #409eff;
    }
  }

  .article-date {
    font-size: 13px;
    color: #c0c4cc;
  }

  .article-views {
    font-size: 12px;
    color: #909399;
    min-width: 50px;
    text-align: right;
  }
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
  .archive-layout {
    flex-direction: column;
  }
}
</style>
