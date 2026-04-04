import { defineStore } from 'pinia'
import {
  getOperationLogPage,
  deleteOperationLogs
} from '@/api/operationLog'

export const useOperationLogStore = defineStore('operationLog', {
  state: () => ({
    list: [],
    total: 0,
    loading: false
  }),

  actions: {
    async fetchList(params) {
      this.loading = true
      try {
        const res = await getOperationLogPage(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    },

    async remove(ids) {
      return deleteOperationLogs(ids)
    }
  }
})
