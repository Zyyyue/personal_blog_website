import { defineStore } from 'pinia'
import {
  getArticlePage,
  getArticleById,
  createArticle,
  updateArticle,
  deleteArticles,
  togglePublish,
  toggleTop,
  searchArticles
} from '@/api/article'

export const useArticleStore = defineStore('article', {
  state: () => ({
    list: [],
    total: 0,
    loading: false,
    current: null
  }),

  actions: {
    async fetchList(params) {
      this.loading = true
      try {
        const res = await getArticlePage(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    },

    async fetchDetail(id) {
      const res = await getArticleById(id)
      this.current = res.data
      return res
    },

    async create(data) {
      return createArticle(data)
    },

    async update(data) {
      return updateArticle(data)
    },

    async remove(ids) {
      return deleteArticles(ids)
    },

    async toggleArticlePublish(id, isPublished) {
      return togglePublish(id, isPublished)
    },

    async toggleArticleTop(id, isTop) {
      return toggleTop(id, isTop)
    },

    async search(params) {
      this.loading = true
      try {
        const res = await searchArticles(params)
        this.list = res.data?.records || []
        this.total = res.data?.total || 0
        return res
      } finally {
        this.loading = false
      }
    }
  }
})
