import { defineStore } from 'pinia'
import {
  getExperienceList,
  addExperience,
  updateExperience,
  deleteExperiences
} from '@/api/experience'

export const useExperienceStore = defineStore('experience', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList(types = [1, 2, 3]) {
      this.loading = true
      try {
        const res = await getExperienceList({ types })
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addExperience(data)
    },

    async update(data) {
      return updateExperience(data)
    },

    async remove(ids) {
      return deleteExperiences(ids)
    }
  }
})
