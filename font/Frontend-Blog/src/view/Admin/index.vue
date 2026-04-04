<template>
  <div class="admin-redirect-page">
    <div class="admin-card">
      <div class="admin-icon">
        <svg viewBox="0 0 24 24" width="64" height="64" fill="none" stroke="currentColor" stroke-width="1.5">
          <path d="M12 2L2 7l10 5 10-5-10-5z" />
          <path d="M2 17l10 5 10-5" />
          <path d="M2 12l10 5 10-5" />
        </svg>
      </div>
      <h2>管理后台入口</h2>
      <p>点击按钮进入博客管理系统</p>
      <a href="/admin" class="admin-btn">
        进入后台
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M5 12h14" />
          <path d="M12 5l7 7-7 7" />
        </svg>
      </a>
    </div>
  </div>
</template>

<style scoped lang="scss">
.admin-redirect-page {
  min-height: 60vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.admin-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  border: 1px solid #ebeef5;
  padding: 48px;
  text-align: center;
  max-width: 400px;
}

.admin-icon {
  color: #409eff;
  margin-bottom: 24px;
}

h2 {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 12px;
}

p {
  font-size: 14px;
  color: #909399;
  margin: 0 0 32px;
}

.admin-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 32px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  text-decoration: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  transition: transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.4);

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 24px rgba(102, 126, 234, 0.5);
  }
}
</style>
