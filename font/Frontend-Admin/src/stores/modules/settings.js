import { defineStore } from 'pinia'
import {
  getSystemConfig,
  saveSystemConfig
} from '@/api/settings'

export const useSettingsStore = defineStore('settings', {
  state: () => ({
    config: {}
  }),

  actions: {
    async fetchConfig() {
      const res = await getSystemConfig()
      // 后端返回的是数组列表，需要转换为对象
      if (res.data && Array.isArray(res.data)) {
        // 将数组转换为对象，key 为 configKey，value 为 configValue
        const configObj = {}
        res.data.forEach(item => {
          configObj[item.configKey] = item.configValue
        })
        this.config = configObj
        return { ...res, data: configObj }
      }
      return res
    },

    async saveConfig(data) {
      const res = await saveSystemConfig(data)
      if (res.data) {
        this.config = res.data
      }
      return res
    }
  }
})
