import http from '@/utils/request'

/**
 * 获取个人信息
 */
export const getPersonalInfo = () => http.get('/personalInfo')

/**
 * 获取技能列表
 */
export const getSkills = () => http.get('/skill')

/**
 * 获取经历列表
 */
export const getExperiences = (type) =>
  http.get('/experience', { params: { type } })
