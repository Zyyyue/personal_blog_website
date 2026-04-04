import { defineStore } from 'pinia'
import {
  getSocialMedias,
  addSocialMedia,
  updateSocialMedia,
  deleteSocialMedias
} from '@/api/socialMedia'

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
        this.list = res.data || []
        return res
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
