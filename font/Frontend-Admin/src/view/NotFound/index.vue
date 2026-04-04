<template>
  <div class="not-found">
    <div class="content">
      <h1 class="code">404</h1>
      <p class="desc">页面未找到</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}
</script>

<style scoped lang="scss">
.not-found {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;

  .content {
    text-align: center;

    .code {
      font-size: 120px;
      font-weight: 700;
      color: #409eff;
      margin-bottom: 16px;
    }

    .desc {
      font-size: 24px;
      color: #606266;
      margin-bottom: 32px;
    }
  }
}
</style>
