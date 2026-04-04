import { defineStore } from 'pinia'
import { getPersonalInfo } from '@/api/personalInfo'

export const useBlogStore = defineStore('blog', {
  state: () => ({
    personalInfo: null,
    siteConfig: null
  }),

  actions: {
    async fetchPersonalInfo() {
      const res = await getPersonalInfo()
      this.personalInfo = res.data
      return res
    },

    async fetchSiteConfig() {
      // TODO: 调用 API 获取网站配置
      this.siteConfig = {
        title: '个人博客',
        subtitle: '记录成长与生活',
        keywords: '博客，技术，生活',
        description: '个人博客 - 记录成长与生活'
      }
      return this.siteConfig
    }
  }
})
