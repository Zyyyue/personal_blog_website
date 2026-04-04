import http from '@/utils/request'

/**
 * 获取所有标签
 */
export const getTags = () => http.get('/admin/article/tag')

/**
 * 添加标签
 * @param {object} data
 */
export const addTag = (data) => http.post('/admin/article/tag', data)

/**
 * 更新标签
 * @param {object} data
 */
export const updateTag = (data) => http.put('/admin/article/tag', data)

/**
 * 批量删除标签
 * @param {number[]} ids
 */
export const deleteTags = (ids) =>
  http.delete('/admin/article/tag', { params: { ids: ids.join(',') } })
