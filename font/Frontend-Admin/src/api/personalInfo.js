import http from '@/utils/request'

/**
 * 获取个人信息
 */
export const getPersonalInfo = () => http.get('/admin/personalInfo')

/**
 * 更新个人信息
 * @param {object} data
 */
export const updatePersonalInfo = (data) =>
  http.put('/admin/personalInfo', data)
