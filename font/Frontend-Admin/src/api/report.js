import http from '@/utils/request'

/**
 * 浏览量统计
 * @param {{ begin: string, end: string }} params
 */
export const getViewStatistics = (params) =>
  http.get('/admin/report/viewStatistics', { params })

/**
 * 访客统计
 * @param {{ begin: string, end: string }} params
 */
export const getVisitorStatistics = (params) =>
  http.get('/admin/report/visitorStatistics', { params })

/**
 * 访客省份分布
 */
export const getProvinceDistribution = () =>
  http.get('/admin/report/provinceDistribution')

/**
 * 文章访问 Top10
 */
export const getArticleViewTop10 = () =>
  http.get('/admin/report/articleViewTop10')

/**
 * 总览数据
 */
export const getOverview = () => http.get('/admin/report/overview')

/**
 * 数据统计
 */
export const getStat = () => http.get('/admin/report/stat')

/**
 * 访问趋势
 */
export const getViewTrend = () => http.get('/admin/report/viewTrend')

/**
 * 访客来源
 */
export const getVisitorSource = () => http.get('/admin/report/visitorSource')

/**
 * 文章统计
 */
export const getArticleStat = () => http.get('/admin/report/articleStat')
