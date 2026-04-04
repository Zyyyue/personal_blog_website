import http from '@/utils/request'

/**
 * 获取友链列表
 */
export const getFriendLinks = () => http.get('/friendLink')
