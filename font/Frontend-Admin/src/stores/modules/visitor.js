import { defineStore } from 'pinia'
import {
  getVisitorPage,
  blockVisitors,
  unblockVisitors,
  updateVisitor,
  deleteVisitors
} from '@/api/visitor'

export const useVisitorStore = defineStore('visitor', {
  state: () => ({
    list: [],
    total: 0,
    loading: false
  }),

  actions: {
    async fetchList(params) {
      this.loading = true
      try {
        const res = await getVisitorPage(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    },

    async block(ids) {
      return blockVisitors(ids)
    },

    async unblock(ids) {
      return unblockVisitors(ids)
    },

    async update(data) {
      return updateVisitor(data)
    },

    async remove(ids) {
      return deleteVisitors(ids)
    }
  }
})
