import { defineStore } from 'pinia'
import {
  getCommentPage,
  approveComments,
  deleteComments,
  replyComment
} from '@/api/comment'

export const useCommentStore = defineStore('comment', {
  state: () => ({
    list: [],
    total: 0,
    loading: false
  }),

  actions: {
    async fetchList(params) {
      this.loading = true
      try {
        const res = await getCommentPage(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    },

    async approve(ids) {
      return approveComments(ids)
    },

    async remove(ids) {
      return deleteComments(ids)
    },

    async reply(data) {
      return replyComment(data)
    }
  }
})
