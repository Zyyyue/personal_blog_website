import http from '@/utils/request'

/**
 * 分页查询访客
 * @param {{ page: number, pageSize: number }} params
 */
export const getVisitorPage = (params) =>
  http.get('/admin/visitor/page', { params })

/**
 * 批量封禁访客
 * @param {number[]} ids
 */
export const blockVisitors = (ids) =>
  http.put('/admin/visitor/block', null, { params: { ids: ids.join(',') } })

/**
 * 批量解封访客
 * @param {number[]} ids
 */
export const unblockVisitors = (ids) =>
  http.put('/admin/visitor/unblock', null, { params: { ids: ids.join(',') } })

/**
 * 更新访客
 * @param {object} data
 */
export const updateVisitor = (data) => http.put('/admin/visitor', data)

/**
 * 批量删除访客
 * @param {number[]} ids
 */
export const deleteVisitors = (ids) =>
  http.delete('/admin/visitor', { params: { ids: ids.join(',') } })
