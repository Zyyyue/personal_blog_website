<template>
  <div class="not-found-page">
    <div class="not-found-card">
      <div class="error-code">404</div>
      <h1>页面走丢了</h1>
      <p>抱歉，您访问的页面不存在</p>
      <router-link to="/" class="back-btn">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M19 12H5" />
          <path d="M12 19l-7-7 7-7" />
        </svg>
        返回首页
      </router-link>
    </div>
  </div>
</template>

<style scoped lang="scss">
.not-found-page {
  min-height: 60vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.not-found-card {
  text-align: center;
  padding: 48px;
}

.error-code {
  font-size: 120px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  line-height: 1;
  margin-bottom: 24px;
}

h1 {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 12px;
}

p {
  font-size: 14px;
  color: #909399;
  margin: 0 0 32px;
}

.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  background: #409eff;
  color: #fff;
  text-decoration: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s;

  &:hover {
    background: #66b1ff;
    transform: translateY(-2px);
  }
}
</style>
