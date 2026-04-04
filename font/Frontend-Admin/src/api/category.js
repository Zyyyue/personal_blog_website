import http from '@/utils/request'

/**
 * 获取所有分类
 */
export const getCategories = () => http.get('/admin/articleCategory')

/**
 * 添加分类
 * @param {object} data
 */
export const addCategory = (data) => http.post('/admin/articleCategory', data)

/**
 * 更新分类
 * @param {object} data
 */
export const updateCategory = (data) => http.put('/admin/articleCategory', data)

/**
 * 批量删除分类
 * @param {number[]} ids
 */
export const deleteCategories = (ids) =>
  http.delete('/admin/articleCategory', { params: { ids: ids.join(',') } })
