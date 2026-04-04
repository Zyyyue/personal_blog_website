<script setup>
import { ref, onMounted } from 'vue'
import SidebarCard from '@/components/SidebarCard.vue'

const messages = ref([])
const loading = ref(false)
const page = ref(1)
const total = ref(0)

const formLoading = ref(false)
const messageForm = ref({
  nickname: '',
  email: '',
  content: ''
})

const submitMessage = async () => {
  if (!messageForm.value.nickname || !messageForm.value.email || !messageForm.value.content) {
    alert('请填写完整信息')
    return
  }
  formLoading.value = true
  try {
    // TODO: 调用 API 提交留言
    console.log('提交留言:', messageForm.value)
    alert('留言成功，等待审核')
    messageForm.value = { nickname: '', email: '', content: '' }
  } catch (error) {
    console.error(error)
  } finally {
    formLoading.value = false
  }
}

const loadMessages = async () => {
  loading.value = true
  try {
    // TODO: 调用 API 获取留言列表
    messages.value = [
      {
        id: 1,
        nickname: '张三',
        content: '博主写得很好，继续加油！',
        createTime: '2024-01-15 10:30:00',
        reply: '感谢支持！'
      },
      {
        id: 2,
        nickname: '李四',
        content: '这个主题很漂亮，求分享源码',
        createTime: '2024-01-14 15:20:00',
        reply: null
      }
    ]
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadMessages()
})
</script>

<template>
  <div class="message-page">
    <div class="message-layout">
      <div class="message-main">
        <!-- 留言表单 -->
        <div class="message-card">
          <h2 class="card-title">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z" />
            </svg>
            写留言
          </h2>
          <div class="message-form">
            <el-form :model="messageForm" label-width="80px">
              <el-form-item label="昵称">
                <el-input v-model="messageForm.nickname" placeholder="请输入昵称" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="messageForm.email" type="email" placeholder="请输入邮箱（选填）" />
              </el-form-item>
              <el-form-item label="留言内容">
                <el-input
                  v-model="messageForm.content"
                  type="textarea"
                  :rows="5"
                  placeholder="请输入留言内容"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" :loading="formLoading" @click="submitMessage">
                  提交留言
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>

        <!-- 留言列表 -->
        <div class="message-card">
          <h2 class="card-title">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z" />
            </svg>
            留言列表
          </h2>
          <div v-if="loading" class="loading-placeholder">
            <el-skeleton :rows="3" animated />
          </div>
          <div v-else-if="messages.length" class="message-list">
            <div v-for="msg in messages" :key="msg.id" class="message-item">
              <div class="message-header">
                <div class="message-author">
                  <div class="author-avatar">{{ msg.nickname[0] }}</div>
                  <div class="author-info">
                    <span class="author-name">{{ msg.nickname }}</span>
                    <span class="message-time">{{ msg.createTime }}</span>
                  </div>
                </div>
              </div>
              <div class="message-content">{{ msg.content }}</div>
              <div v-if="msg.reply" class="message-reply">
                <div class="reply-label">博主回复：</div>
                <div class="reply-content">{{ msg.reply }}</div>
              </div>
            </div>
          </div>
          <div v-else class="empty-tip">暂无留言，快来抢沙发吧~</div>
        </div>
      </div>

      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.message-page {
  width: 100%;
}

.message-layout {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.message-main {
  flex: 1;
  min-width: 0;
}

.message-card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  padding: 24px;
  margin-bottom: 20px;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #ebeef5;
}

.message-form {
  :deep(.el-form-item) {
    margin-bottom: 20px;
  }
}

.loading-placeholder {
  padding: 20px 0;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message-item {
  padding: 20px;
  background: #fafafa;
  border-radius: 8px;
}

.message-header {
  margin-bottom: 12px;
}

.message-author {
  display: flex;
  align-items: center;
  gap: 12px;
}

.author-avatar {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-size: 16px;
  font-weight: 600;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.author-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.author-name {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}

.message-time {
  font-size: 12px;
  color: #909399;
}

.message-content {
  font-size: 14px;
  color: #606266;
  line-height: 1.7;
  margin-bottom: 12px;
  white-space: pre-wrap;
}

.message-reply {
  padding: 12px 16px;
  background: #fff;
  border-radius: 6px;
  border-left: 3px solid #409eff;
}

.reply-label {
  font-size: 12px;
  color: #909399;
  margin-bottom: 6px;
}

.reply-content {
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
}

.empty-tip {
  padding: 40px 0;
  text-align: center;
  color: #909399;
  font-size: 14px;
}

@media (max-width: 960px) {
  .message-layout {
    flex-direction: column;
  }
}
</style>
