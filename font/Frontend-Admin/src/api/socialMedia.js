import http from '@/utils/request'

/**
 * 获取所有社交媒体
 */
export const getSocialMedias = () => http.get('/admin/socialMedia')

/**
 * 添加社交媒体
 * @param {object} data
 */
export const addSocialMedia = (data) => http.post('/admin/socialMedia', data)

/**
 * 更新社交媒体
 * @param {object} data
 */
export const updateSocialMedia = (data) => http.put('/admin/socialMedia', data)

/**
 * 批量删除社交媒体
 * @param {number[]} ids
 */
export const deleteSocialMedias = (ids) =>
  http.delete('/admin/socialMedia', { params: { ids: ids.join(',') } })
