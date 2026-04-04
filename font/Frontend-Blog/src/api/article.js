import http from '@/utils/request'

/**
 * 获取文章列表
 */
export const getArticlePage = (page, pageSize, params) =>
  http.get('/article/page', { params: { page, pageSize, ...params } })

/**
 * 根据 ID 获取文章
 */
export const getArticleById = (id) => http.get(`/article/${id}`)

/**
 * 搜索文章
 */
export const searchArticles = (keyword, page, pageSize) =>
  http.get('/article/search', { params: { keyword, page, pageSize } })

/**
 * 获取热门文章
 */
export const getHotArticles = (limit) =>
  http.get('/article/hot', { params: { limit } })

/**
 * 获取文章归档
 */
export const getArchives = () => http.get('/article/archive')
