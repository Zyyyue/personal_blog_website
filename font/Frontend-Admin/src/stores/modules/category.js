import { defineStore } from 'pinia'
import {
  getCategories,
  addCategory,
  updateCategory,
  deleteCategories
} from '@/api/category'

export const useCategoryStore = defineStore('category', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getCategories()
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addCategory(data)
    },

    async update(data) {
      return updateCategory(data)
    },

    async remove(ids) {
      return deleteCategories(ids)
    }
  }
})
