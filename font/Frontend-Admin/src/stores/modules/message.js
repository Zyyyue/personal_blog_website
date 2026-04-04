import { defineStore } from 'pinia'
import {
  getMessagePage,
  approveMessages,
  deleteMessages,
  replyMessage
} from '@/api/message'

export const useMessageStore = defineStore('message', {
  state: () => ({
    list: [],
    total: 0,
    loading: false
  }),

  actions: {
    async fetchList(params) {
      this.loading = true
      try {
        const res = await getMessagePage(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    },

    async approve(ids) {
      return approveMessages(ids)
    },

    async remove(ids) {
      return deleteMessages(ids)
    },

    async reply(data) {
      return replyMessage(data)
    }
  }
})
