import http from '@/utils/request'

/**
 * 获取所有技能
 */
export const getSkills = () => http.get('/admin/skill')

/**
 * 添加技能
 * @param {object} data
 */
export const addSkill = (data) => http.post('/admin/skill', data)

/**
 * 更新技能
 * @param {object} data
 */
export const updateSkill = (data) => http.put('/admin/skill', data)

/**
 * 批量删除技能
 * @param {number[]} ids
 */
export const deleteSkills = (ids) =>
  http.delete('/admin/skill', { params: { ids: ids.join(',') } })
