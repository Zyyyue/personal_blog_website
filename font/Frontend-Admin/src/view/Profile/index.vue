<template>
  <div class="profile-page">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="user-info">
            <el-avatar :size="100" :icon="User" />
            <div class="nickname">{{ userStore.nickname }}</div>
            <div class="username">{{ userStore.username }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>修改密码</template>
          <el-form
            ref="passwordFormRef"
            :model="passwordForm"
            :rules="passwordRules"
            label-width="100px"
          >
            <el-form-item label="当前密码" prop="oldPassword">
              <el-input
                v-model="passwordForm.oldPassword"
                type="password"
                placeholder="请输入当前密码"
                show-password
              />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input
                v-model="passwordForm.newPassword"
                type="password"
                placeholder="请输入新密码"
                show-password
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                v-model="passwordForm.confirmPassword"
                type="password"
                placeholder="请确认新密码"
                show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="passwordLoading" @click="handlePasswordSubmit">
                修改密码
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>个人信息</template>
          <el-form
            ref="profileFormRef"
            :model="profileForm"
            :rules="profileRules"
            label-width="100px"
          >
            <el-form-item label="用户名" prop="username">
              <el-input v-model="profileForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="profileForm.nickname" placeholder="请输入昵称" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="profileForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="profileForm.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="profileLoading" @click="handleProfileSubmit">
                保存修改
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/stores'
import { User } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const passwordFormRef = ref(null)
const profileFormRef = ref(null)
const passwordLoading = ref(false)
const profileLoading = ref(false)

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const profileForm = reactive({
  username: '',
  nickname: '',
  email: '',
  phone: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入当前密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' }
  ],
  confirmPassword: [{ required: true, validator: validateConfirmPassword, trigger: 'blur' }]
}

const profileRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }]
}

const handlePasswordSubmit = async () => {
  await passwordFormRef.value.validate(async (valid) => {
    if (!valid) return

    passwordLoading.value = true
    try {
      await userStore.changePassword({
        oldPassword: passwordForm.oldPassword,
        newPassword: passwordForm.newPassword
      })
      ElMessage.success('密码修改成功')
      passwordForm.oldPassword = ''
      passwordForm.newPassword = ''
      passwordForm.confirmPassword = ''
    } catch (error) {
      console.error(error)
    } finally {
      passwordLoading.value = false
    }
  })
}

const handleProfileSubmit = async () => {
  await profileFormRef.value.validate(async (valid) => {
    if (!valid) return

    profileLoading.value = true
    try {
      await userStore.updateProfile(profileForm)
      ElMessage.success('个人信息修改成功')
      userStore.setNickname(profileForm.nickname)
    } catch (error) {
      console.error(error)
    } finally {
      profileLoading.value = false
    }
  })
}

onMounted(() => {
  profileForm.username = userStore.username
  profileForm.nickname = userStore.nickname
})
</script>

<style scoped lang="scss">
.profile-page {
  padding: 20px;

  .user-info {
    text-align: center;
    padding: 20px 0;

    .nickname {
      font-size: 18px;
      font-weight: 600;
      margin-top: 16px;
      color: #303133;
    }

    .username {
      font-size: 14px;
      color: #909399;
      margin-top: 8px;
    }
  }
}
</style>
