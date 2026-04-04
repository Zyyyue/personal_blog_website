import { defineStore } from 'pinia'
import {
  getFriendLinks,
  addFriendLink,
  updateFriendLink,
  deleteFriendLinks
} from '@/api/friendLink'

export const useFriendLinkStore = defineStore('friendLink', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getFriendLinks()
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addFriendLink(data)
    },

    async update(data) {
      return updateFriendLink(data)
    },

    async remove(ids) {
      return deleteFriendLinks(ids)
    }
  }
})
