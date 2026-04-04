import http from '@/utils/request'

/**
 * 获取所有音乐
 */
export const getMusics = () => http.get('/admin/music')

/**
 * 根据 ID 获取音乐
 * @param {number} id
 */
export const getMusicById = (id) => http.get(`/admin/music/${id}`)

/**
 * 分页查询音乐
 * @param {{ page: number, pageSize: number }} params
 */
export const getMusicPage = (params) =>
  http.get('/admin/music/page', { params })

/**
 * 添加音乐
 * @param {object} data
 */
export const addMusic = (data) => http.post('/admin/music', data)

/**
 * 更新音乐
 * @param {object} data
 */
export const updateMusic = (data) => http.put('/admin/music', data)

/**
 * 批量删除音乐
 * @param {number[]} ids
 */
export const deleteMusics = (ids) =>
  http.delete('/admin/music', { params: { ids: ids.join(',') } })
