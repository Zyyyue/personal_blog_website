import { defineStore } from 'pinia'
import { getTags, addTag, updateTag, deleteTags } from '@/api/tag'

export const useTagStore = defineStore('tag', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getTags()
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addTag(data)
    },

    async update(data) {
      return updateTag(data)
    },

    async remove(ids) {
      return deleteTags(ids)
    }
  }
})
