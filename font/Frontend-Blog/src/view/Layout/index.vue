<template>
  <div class="blog-layout">
    <!-- 顶部导航 -->
    <header class="blog-header">
      <div class="header-inner">
        <div class="header-left">
          <router-link to="/" class="logo">
            <span class="logo-icon">✦</span>
            <span class="logo-text">个人博客</span>
          </router-link>
        </div>
        <nav class="header-nav">
          <router-link to="/" class="nav-item" active-class="active">首页</router-link>
          <router-link to="/blog" class="nav-item" active-class="active">博客</router-link>
          <router-link to="/cv" class="nav-item" active-class="active">简历</router-link>
          <router-link to="/about" class="nav-item" active-class="active">关于</router-link>
          <router-link to="/message" class="nav-item" active-class="active">留言</router-link>
        </nav>
        <div class="header-right">
          <div class="search-box">
            <input
              v-model="searchKeyword"
              type="text"
              placeholder="搜索文章..."
              @keyup.enter="handleSearch"
            />
            <button class="search-btn" @click="handleSearch">
              <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8" />
                <path d="m21 21-4.35-4.35" />
              </svg>
            </button>
          </div>
          <a :href="adminUrl" class="admin-link" title="管理后台">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M12 20h9" />
              <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z" />
            </svg>
          </a>
        </div>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="blog-main">
      <router-view />
    </main>

    <!-- 页脚 -->
    <footer class="blog-footer">
      <div class="footer-inner">
        <div class="footer-links">
          <a href="https://beian.miit.gov.cn/" target="_blank" rel="noopener">{{ icpNumber }}</a>
        </div>
        <p class="footer-text">
          © {{ currentYear }} 个人博客。All rights reserved.
        </p>
        <p class="footer-text">
          Powered by SpringBoot & Vue3
        </p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const searchKeyword = ref('')
const adminUrl = '/admin'
const currentYear = new Date().getFullYear()
const icpNumber = '赣 ICP 备 XXXXXXXX 号'

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({ path: '/blog', query: { search: searchKeyword.value } })
  }
}
</script>

<style scoped lang="scss">
.blog-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 头部 */
.blog-header {
  background: #fff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-inner {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-left {
  .logo {
    display: flex;
    align-items: center;
    gap: 10px;
    text-decoration: none;
    color: #303133;
    font-size: 18px;
    font-weight: 600;

    .logo-icon {
      font-size: 24px;
      color: #409eff;
    }
  }
}

.header-nav {
  display: flex;
  gap: 8px;

  .nav-item {
    padding: 8px 16px;
    border-radius: 6px;
    text-decoration: none;
    color: #606266;
    font-size: 14px;
    transition: all 0.2s;

    &:hover {
      background: #f5f7fa;
      color: #409eff;
    }

    &.active {
      background: #409eff;
      color: #fff;
    }
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;

  .search-box {
    display: flex;
    align-items: center;
    border: 1px solid #e4e7ed;
    border-radius: 20px;
    padding: 4px 12px;
    background: #f5f7fa;
    transition: all 0.2s;

    &:focus-within {
      border-color: #409eff;
      background: #fff;
    }

    input {
      border: none;
      background: transparent;
      outline: none;
      padding: 4px 8px;
      font-size: 13px;
      width: 120px;
      color: #303133;

      &::placeholder {
        color: #c0c4cc;
      }
    }

    .search-btn {
      border: none;
      background: transparent;
      cursor: pointer;
      color: #909399;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 4px;

      &:hover {
        color: #409eff;
      }
    }
  }

  .admin-link {
    color: #909399;
    transition: color 0.2s;

    &:hover {
      color: #409eff;
    }
  }
}

/* 主内容 */
.blog-main {
  flex: 1;
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px 20px;
  width: 100%;
}

/* 页脚 */
.blog-footer {
  background: #fff;
  border-top: 1px solid #e6e6e6;
  padding: 24px 20px;
  margin-top: auto;
}

.footer-inner {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.footer-links {
  margin-bottom: 12px;

  a {
    color: #909399;
    text-decoration: none;
    font-size: 13px;

    &:hover {
      color: #606266;
    }
  }
}

.footer-text {
  color: #c0c4cc;
  font-size: 13px;
  margin: 4px 0;
}

@media (max-width: 768px) {
  .header-nav {
    display: none;
  }

  .header-right {
    .search-box {
      display: none;
    }
  }
}
</style>
