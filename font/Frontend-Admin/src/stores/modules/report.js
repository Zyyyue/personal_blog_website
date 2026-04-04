import { defineStore } from 'pinia'
import {
  getViewStatistics,
  getVisitorStatistics,
  getProvinceDistribution,
  getArticleViewTop10,
  getOverview,
  getStat,
  getViewTrend,
  getVisitorSource,
  getArticleStat
} from '@/api/report'

export const useReportStore = defineStore('report', {
  state: () => ({
    overview: null,
    viewStatistics: [],
    visitorStatistics: [],
    provinceDistribution: [],
    articleViewTop10: [],
    loading: false
  }),

  actions: {
    async fetchOverview() {
      const res = await getOverview()
      this.overview = res.data
      return res
    },

    async fetchViewStatistics(params) {
      const res = await getViewStatistics(params)
      this.viewStatistics = res.data || []
      return res
    },

    async fetchVisitorStatistics(params) {
      const res = await getVisitorStatistics(params)
      this.visitorStatistics = res.data || []
      return res
    },

    async fetchProvinceDistribution() {
      const res = await getProvinceDistribution()
      this.provinceDistribution = res.data || []
      return res
    },

    async fetchArticleViewTop10() {
      const res = await getArticleViewTop10()
      this.articleViewTop10 = res.data || []
      return res
    },

    async fetchStat() {
      return getStat()
    },

    async fetchViewTrend() {
      return getViewTrend()
    },

    async fetchVisitorSource() {
      return getVisitorSource()
    },

    async fetchArticleStat() {
      return getArticleStat()
    }
  }
})
