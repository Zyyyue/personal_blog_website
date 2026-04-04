import http from '@/utils/request'

/**
 * 获取所有 RSS 链接
 */
export const getRssSubscriptions = () => http.get('/admin/rssLink')

/**
 * 添加 RSS 链接
 * @param {object} data
 */
export const addRssSubscription = (data) =>
  http.post('/admin/rssLink', data)

/**
 * 根据 ID 获取订阅
 * @param {number} id
 */
export const getRssSubscriptionById = (id) =>
  http.get(`/admin/rssLink/${id}`)

/**
 * 分页查询订阅
 * @param {{ page: number, pageSize: number }} params
 */
export const getRssSubscriptionPage = (params) =>
  http.get('/admin/rssSubscription/page', { params })

/**
 * 更新订阅
 * @param {object} data
 */
export const updateRssSubscription = (data) =>
  http.put('/admin/rssLink', data)

/**
 * 批量删除订阅
 * @param {number[]} ids
 */
export const deleteRssSubscriptions = (ids) =>
  http.delete('/admin/rssLink', { params: { ids: ids.join(',') } })
