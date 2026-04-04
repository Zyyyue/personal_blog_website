import http from '@/utils/request'

/**
 * 管理员登录
 * @param {{ username: string, password: string, code: string, uuid?: string }} params
 */
export const login = (params) => http.post('/admin/login', params)

/**
 * 获取管理员信息
 */
export const getAdminInfo = () => http.get('/admin/')

/**
 * 修改管理员密码
 * @param {{ oldPassword: string, newPassword: string }} params
 */
export const changePassword = (params) =>
  http.put('/admin/changePassword', params)

/**
 * 修改管理员昵称
 * @param {{ nickname: string }} params
 */
export const changeNickname = (params) =>
  http.put('/admin/changeNickname', params)

/**
 * 换绑管理员邮箱
 * @param {{ email: string, code: string }} params
 */
export const changeEmail = (params) =>
  http.put('/admin/changeEmail', params)

/**
 * 发送验证码
 * @param {{ username: string }} params
 */
export const sendCode = (params) => http.post('/admin/sendCode', params)

/**
 * 退出登录
 */
export const logout = () => http.post('/admin/logout')

/**
 * 更新个人信息
 * @param {{ username: string, nickname: string, email: string, phone: string }} params
 */
export const updateProfile = (params) =>
  http.put('/admin/updateProfile', params)
