import http from '@/utils/request'

/**
 * 获取所有友链
 */
export const getFriendLinks = () => http.get('/admin/friendLink')

/**
 * 添加友链
 * @param {object} data
 */
export const addFriendLink = (data) => http.post('/admin/friendLink', data)

/**
 * 更新友链
 * @param {object} data
 */
export const updateFriendLink = (data) => http.put('/admin/friendLink', data)

/**
 * 批量删除友链
 * @param {number[]} ids
 */
export const deleteFriendLinks = (ids) =>
  http.delete('/admin/friendLink', { params: { ids: ids.join(',') } })
