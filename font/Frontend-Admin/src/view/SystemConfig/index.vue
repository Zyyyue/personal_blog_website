<template>
  <div class="system-config-page">
    <el-card>
      <template #header>系统配置</template>
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="网站标题" prop="siteTitle">
          <el-input v-model="formData.siteTitle" placeholder="请输入网站标题" />
        </el-form-item>
        <el-form-item label="网站副标题" prop="siteSubtitle">
          <el-input v-model="formData.siteSubtitle" placeholder="请输入网站副标题" />
        </el-form-item>
        <el-form-item label="网站关键词" prop="siteKeywords">
          <el-input
            v-model="formData.siteKeywords"
            type="textarea"
            :rows="2"
            placeholder="请输入网站关键词，多个关键词用逗号分隔"
          />
        </el-form-item>
        <el-form-item label="网站描述" prop="siteDescription">
          <el-input
            v-model="formData.siteDescription"
            type="textarea"
            :rows="3"
            placeholder="请输入网站描述"
          />
        </el-form-item>
        <el-form-item label="作者名称" prop="authorName">
          <el-input v-model="formData.authorName" placeholder="请输入作者名称" />
        </el-form-item>
        <el-form-item label="作者头像" prop="authorAvatar">
          <el-input v-model="formData.authorAvatar" placeholder="请输入作者头像地址" />
        </el-form-item>
        <el-form-item label="网站 Logo" prop="siteLogo">
          <el-input v-model="formData.siteLogo" placeholder="请输入网站 Logo 地址" />
        </el-form-item>
        <el-form-item label="GitHub 地址" prop="githubUrl">
          <el-input v-model="formData.githubUrl" placeholder="请输入 GitHub 地址" />
        </el-form-item>
        <el-form-item label="Gitee 地址" prop="giteeUrl">
          <el-input v-model="formData.giteeUrl" placeholder="请输入 Gitee 地址" />
        </el-form-item>
        <el-form-item label="QQ 号码" prop="qqNumber">
          <el-input v-model="formData.qqNumber" placeholder="请输入 QQ 号码" />
        </el-form-item>
        <el-form-item label="微信二维码" prop="wechatQrCode">
          <el-input v-model="formData.wechatQrCode" placeholder="请输入微信二维码地址" />
        </el-form-item>
        <el-form-item label="支付宝二维码" prop="alipayQrCode">
          <el-input v-model="formData.alipayQrCode" placeholder="请输入支付宝二维码地址" />
        </el-form-item>
        <el-form-item label="ICP 备案号" prop="icpNumber">
          <el-input v-model="formData.icpNumber" placeholder="请输入 ICP 备案号" />
        </el-form-item>
        <el-form-item label="公安备案号" prop="policeNumber">
          <el-input v-model="formData.policeNumber" placeholder="请输入公安备案号" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleSubmit">
            保存配置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useSettingsStore } from '@/stores'
import { ElMessage } from 'element-plus'

const settingsStore = useSettingsStore()

const formRef = ref(null)
const loading = ref(false)

const formData = reactive({
  siteTitle: '',
  siteSubtitle: '',
  siteKeywords: '',
  siteDescription: '',
  authorName: '',
  authorAvatar: '',
  siteLogo: '',
  githubUrl: '',
  giteeUrl: '',
  qqNumber: '',
  wechatQrCode: '',
  alipayQrCode: '',
  icpNumber: '',
  policeNumber: ''
})

const rules = {}

const load = async () => {
  const res = await settingsStore.fetchConfig()
  if (res.data) {
    Object.assign(formData, res.data)
  }
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      await settingsStore.saveConfig(formData)
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
.system-config-page {
  padding: 20px;
}
</style>
