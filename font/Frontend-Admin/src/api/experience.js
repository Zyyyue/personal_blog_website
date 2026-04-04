import http from '@/utils/request'

/**
 * 获取经历列表 (管理端)
 * @param {{ types: number[] }} params
 */
export const getExperienceList = (params) => {
  // 将数组转换为逗号分隔的字符串
  const formattedParams = {}
  if (params?.types && Array.isArray(params.types)) {
    formattedParams.types = params.types.join(',')
  }
  return http.get('/admin/experience', { params: formattedParams })
}

/**
 * 添加经历
 * @param {object} data
 */
export const addExperience = (data) => http.post('/admin/experience', data)

/**
 * 更新经历
 * @param {object} data
 */
export const updateExperience = (data) => http.put('/admin/experience', data)

/**
 * 批量删除经历
 * @param {number[]} ids
 */
export const deleteExperiences = (ids) =>
  http.delete('/admin/experience', { params: { ids: ids.join(',') } })
