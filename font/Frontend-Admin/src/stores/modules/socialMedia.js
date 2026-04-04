import { defineStore } from 'pinia'
import {
  getSocialMedias,
  addSocialMedia,
  updateSocialMedia,
  deleteSocialMedias
} from '@/api/socialMedia'
import { ElMessage } from 'element-plus'

export const useSocialMediaStore = defineStore('socialMedia', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getSocialMedias()
        console.log('=== 社交媒体 API 响应 ===', res)
        // 检查后端返回的错误信息
        if (res.code === 0 || (res.msg && res.code !== 200)) {
          console.log('后端返回错误:', res.msg)
          ElMessage.warning(res.msg || '暂无社交媒体数据')
          this.list = []
          return res
        }
        console.log('社交媒体数据:', res.data)
        console.log('设置 list 前的长度:', res.data?.length)
        // 使用数组展开确保响应式更新
        this.list = res.data ? [...res.data] : []
        console.log('设置 list 后的长度:', this.list.length)
        return res
      } catch (error) {
        console.error('获取社交媒体失败', error)
        ElMessage.error('获取失败：' + (error.msg || error.message))
        throw error
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addSocialMedia(data)
    },

    async update(data) {
      return updateSocialMedia(data)
    },

    async remove(ids) {
      return deleteSocialMedias(ids)
    }
  }
})
