import { defineStore } from 'pinia'
import {
  getViewRecordPage,
  deleteViewRecords
} from '@/api/viewRecord'

export const useViewRecordStore = defineStore('viewRecord', {
  state: () => ({
    list: [],
    total: 0,
    loading: false
  }),

  actions: {
    async fetchList(params) {
      this.loading = true
      try {
        const res = await getViewRecordPage(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    },

    async remove(ids) {
      return deleteViewRecords(ids)
    }
  }
})
