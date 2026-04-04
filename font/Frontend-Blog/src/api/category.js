import http from '@/utils/request'

/**
 * 获取分类列表
 */
export const getCategories = () => http.get('/category')

/**
 * 根据分类获取文章
 */
export const getArticlesByCategory = (categoryId, page, pageSize) =>
  http.get('/article/category', { params: { categoryId, page, pageSize } })
