import http from '@/utils/request'

/**
 * 获取标签列表
 */
export const getTags = () => http.get('/tag')

/**
 * 根据标签获取文章
 */
export const getArticlesByTag = (tagId, page, pageSize) =>
  http.get('/article/tag', { params: { tagId, page, pageSize } })
