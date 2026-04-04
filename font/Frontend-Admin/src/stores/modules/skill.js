import { defineStore } from 'pinia'
import {
  getSkills,
  addSkill,
  updateSkill,
  deleteSkills
} from '@/api/skill'

export const useSkillStore = defineStore('skill', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getSkills()
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addSkill(data)
    },

    async update(data) {
      return updateSkill(data)
    },

    async remove(ids) {
      return deleteSkills(ids)
    }
  }
})
