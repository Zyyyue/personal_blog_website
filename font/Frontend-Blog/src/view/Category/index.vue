<script setup>
import { ref, onMounted } from 'vue'
import SidebarCard from '@/components/SidebarCard.vue'

const categories = ref([])

const loadCategories = async () => {
  // TODO: 调用 API 获取分类列表
  categories.value = [
    { id: 1, name: 'Java', count: 15, description: 'Java 相关技术文章' },
    { id: 2, name: '前端', count: 20, description: '前端开发相关文章' },
    { id: 3, name: '数据库', count: 8, description: '数据库技术分享' },
    { id: 4, name: '随笔', count: 5, description: '生活随笔' }
  ]
}

onMounted(() => {
  loadCategories()
})
</script>

<template>
  <div class="category-page">
    <div class="category-layout">
      <div class="category-main">
        <h1 class="page-title">分类目录</h1>
        <div v-if="categories.length" class="category-list">
          <div v-for="cat in categories" :key="cat.id" class="category-card">
            <router-link :to="`/blog?category=${cat.id}`" class="category-link">
              <div class="category-info">
                <h3 class="category-name">{{ cat.name }}</h3>
                <p class="category-desc">{{ cat.description }}</p>
              </div>
              <div class="category-count">{{ cat.count }} 篇</div>
            </router-link>
          </div>
        </div>
        <div v-else class="empty-tip">暂无分类</div>
      </div>
      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.category-page {
  width: 100%;
}

.category-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.category-main {
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

.category-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.category-card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  transition: transform 0.2s, box-shadow 0.2s;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  }
}

.category-link {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px;
  text-decoration: none;
}

.category-info {
  flex: 1;
}

.category-name {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px;
}

.category-desc {
  font-size: 13px;
  color: #909399;
  margin: 0;
}

.category-count {
  font-size: 14px;
  font-weight: 600;
  color: #409eff;
  background: #ecf5ff;
  padding: 6px 12px;
  border-radius: 6px;
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
  .category-layout {
    flex-direction: column;
  }
}
</style>
