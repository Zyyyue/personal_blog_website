import { defineStore } from 'pinia'
import {
  getRssSubscriptions,
  addRssSubscription,
  updateRssSubscription,
  deleteRssSubscriptions
} from '@/api/rssSubscription'

export const useRssSubscriptionStore = defineStore('rssSubscription', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getRssSubscriptions()
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addRssSubscription(data)
    },

    async update(data) {
      return updateRssSubscription(data)
    },

    async remove(ids) {
      return deleteRssSubscriptions(ids)
    }
  }
})
