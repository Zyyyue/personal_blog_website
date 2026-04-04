import { defineStore } from 'pinia'
import {
  getPersonalInfo,
  updatePersonalInfo
} from '@/api/personalInfo'

export const usePersonalInfoStore = defineStore('personalInfo', {
  state: () => ({
    info: null
  }),

  actions: {
    async fetchInfo() {
      const res = await getPersonalInfo()
      if (res.data) {
        this.info = res.data
      }
      return res
    },

    async update(data) {
      const res = await updatePersonalInfo(data)
      if (res.data) {
        this.info = res.data
      }
      return res
    }
  }
})
