<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">个人博客管理后台</h2>
      <el-form ref="formRef" :model="form" :rules="rules" size="large">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            clearable
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        <el-form-item prop="code">
          <div class="code-input">
            <el-input
              v-model="form.code"
              placeholder="请输入验证码"
              prefix-icon="Key"
              clearable
              @keyup.enter="handleLogin"
            />
            <div class="code-image" @click="refreshCode">
              <span>点击刷新</span>
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            class="login-btn"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'
import { sendCode } from '@/api/auth'

const router = useRouter()
const userStore = useUserStore()

const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  code: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

const refreshCode = () => {
  if (!form.username) {
    ElMessage.warning('请先输入用户名')
    return
  }
  sendCode({ username: form.username })
    .then(() => {
      ElMessage.success('验证码已发送到您的邮箱')
      form.code = ''
    })
    .catch((error) => {
      console.error(error)
    })
}

const handleLogin = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      const res = await userStore.login({
        username: form.username,
        password: form.password,
        code: form.code
      })
      console.log('登录响应:', res)
      ElMessage.success('登录成功')
      // 确保路由跳转
      setTimeout(() => {
        router.push('/')
      }, 500)
    } catch (error) {
      console.error('登录失败:', error)
      // 显示错误消息
      ElMessage.error(error?.msg || '登录失败，请检查账号密码')
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 420px;
  padding: 40px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);

  .login-title {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 32px;
  }

  .login-btn {
    width: 100%;
  }

  .code-input {
    display: flex;
    gap: 12px;

    .code-image {
      width: 110px;
      height: 40px;
      background: #f5f7fa;
      border: 1px solid #dcdfe6;
      border-radius: 4px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 12px;
      color: #909399;

      &:hover {
        border-color: #409eff;
        color: #409eff;
      }
    }
  }
}
</style>
