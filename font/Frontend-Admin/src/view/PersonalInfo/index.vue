<template>
  <div class="personal-info-page">
    <el-card>
      <template #header>个人信息</template>
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="formData.position" placeholder="请输入职位" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="formData.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="个人简介" prop="introduction">
          <el-input
            v-model="formData.introduction"
            type="textarea"
            :rows="4"
            placeholder="请输入个人简介"
          />
        </el-form-item>
        <el-form-item label="头像地址" prop="avatar">
          <el-input v-model="formData.avatar" placeholder="请输入头像地址" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleSubmit">
            保存
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { usePersonalInfoStore } from '@/stores'
import { ElMessage } from 'element-plus'

const personalInfoStore = usePersonalInfoStore()

const formRef = ref(null)
const loading = ref(false)

const formData = reactive({
  name: '',
  position: '',
  email: '',
  phone: '',
  city: '',
  introduction: '',
  avatar: ''
})

const rules = {
  email: [{ type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }]
}

const load = async () => {
  const res = await personalInfoStore.fetchInfo()
  if (res.data) {
    Object.assign(formData, res.data)
  }
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      await personalInfoStore.update(formData)
      ElMessage.success('保存成功')
    } catch (error) {
      console.error(error)
    } finally {
      loading.value = false
    }
  })
}

onMounted(load)
</script>

<style scoped lang="scss">
.personal-info-page {
  padding: 20px;
}
</style>
