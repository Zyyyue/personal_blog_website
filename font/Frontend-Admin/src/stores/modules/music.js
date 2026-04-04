import { defineStore } from 'pinia'
import {
  getMusics,
  addMusic,
  updateMusic,
  deleteMusics
} from '@/api/music'

export const useMusicStore = defineStore('music', {
  state: () => ({
    list: [],
    loading: false
  }),

  actions: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getMusics()
        this.list = res.data || []
        return res
      } finally {
        this.loading = false
      }
    },

    async add(data) {
      return addMusic(data)
    },

    async update(data) {
      return updateMusic(data)
    },

    async remove(ids) {
      return deleteMusics(ids)
    }
  }
})
