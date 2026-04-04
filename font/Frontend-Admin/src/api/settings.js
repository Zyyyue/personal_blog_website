import http from '@/utils/request'

/**
 * 获取系统配置
 */
export const getSystemConfig = () => http.get('/admin/systemConfig')

/**
 * 保存系统配置（批量保存）
 * @param {object} data - 配置对象，key 为 configKey，value 为 configValue
 */
export const saveSystemConfig = (data) => {
  // 将对象转换为数组格式 [{ configKey, configValue }, ...]
  const configArray = Object.entries(data).map(([key, value]) => ({
    configKey: key,
    configValue: value
  }))
  return http.post('/admin/systemConfig/batch', configArray)
}

/**
 * 获取所有系统配置
 */
export const getSystemConfigs = () => http.get('/admin/systemConfig')

/**
 * 根据配置键获取配置
 * @param {string} configKey
 */
export const getConfigByKey = (configKey) =>
  http.get(`/admin/systemConfig/key/${configKey}`)

/**
 * 根据 ID 获取配置
 * @param {number} id
 */
export const getConfigById = (id) => http.get(`/admin/systemConfig/${id}`)

/**
 * 添加配置
 * @param {object} data
 */
export const addConfig = (data) => http.post('/admin/systemConfig', data)

/**
 * 更新配置
 * @param {object} data
 */
export const updateConfig = (data) => http.put('/admin/systemConfig', data)

/**
 * 批量删除配置
 * @param {number[]} ids
 */
export const deleteConfigs = (ids) =>
  http.delete('/admin/systemConfig', { params: { ids: ids.join(',') } })
