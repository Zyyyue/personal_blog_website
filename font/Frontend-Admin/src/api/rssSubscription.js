import http from '@/utils/request'

/**
 * 获取所有 RSS 订阅
 */
export const getRssSubscriptions = () => http.get('/admin/rssSubscription')

/**
 * 添加 RSS 订阅
 * @param {object} data
 */
export const addRssSubscription = (data) =>
  http.post('/admin/rssSubscription', data)

/**
 * 根据 ID 获取订阅
 * @param {number} id
 */
export const getRssSubscriptionById = (id) =>
  http.get(`/admin/rssSubscription/${id}`)

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
  http.put('/admin/rssSubscription', data)

/**
 * 批量删除订阅
 * @param {number[]} ids
 */
export const deleteRssSubscriptions = (ids) =>
  http.delete('/admin/rssSubscription', { params: { ids: ids.join(',') } })
