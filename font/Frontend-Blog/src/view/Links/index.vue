<script setup>
import { ref, onMounted } from 'vue'
import SidebarCard from '@/components/SidebarCard.vue'

const links = ref([])

const loadLinks = async () => {
  // TODO: 调用 API 获取友链列表
  links.value = [
    {
      id: 1,
      name: 'Vue.js 官方文档',
      url: 'https://cn.vuejs.org/',
      logo: 'https://cn.vuejs.org/logo.svg',
      description: '渐进式 JavaScript 框架'
    },
    {
      id: 2,
      name: 'Spring Boot 中文网',
      url: 'https://springboot.io/',
      logo: 'https://spring.io/images/spring-logo.svg',
      description: 'Java 开发框架'
    }
  ]
}

onMounted(() => {
  loadLinks()
})
</script>

<template>
  <div class="links-page">
    <div class="links-layout">
      <div class="links-main">
        <h1 class="page-title">友情链接</h1>
        <div v-if="links.length" class="links-list">
          <div v-for="link in links" :key="link.id" class="link-card">
            <a :href="link.url" target="_blank" rel="noopener" class="link-inner">
              <img v-if="link.logo" :src="link.logo" :alt="link.name" class="link-logo" />
              <div class="link-info">
                <h3 class="link-name">{{ link.name }}</h3>
                <p class="link-desc">{{ link.description }}</p>
              </div>
            </a>
          </div>
        </div>
        <div v-else class="empty-tip">暂无友链</div>
      </div>
      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.links-page {
  width: 100%;
}

.links-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.links-main {
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

.links-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.link-card {
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

.link-inner {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  text-decoration: none;
}

.link-logo {
  width: 48px;
  height: 48px;
  object-fit: contain;
  flex-shrink: 0;
}

.link-info {
  flex: 1;
  min-width: 0;
}

.link-name {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.link-desc {
  font-size: 13px;
  color: #909399;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
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
  .links-layout {
    flex-direction: column;
  }
}
</style>
