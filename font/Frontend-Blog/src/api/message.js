import http from '@/utils/request'

/**
 * 获取留言列表
 */
export const getMessagePage = (page, pageSize, params) =>
  http.get('/message/page', { params: { page, pageSize, ...params } })

/**
 * 提交留言
 */
export const submitMessage = (data) => http.post('/message', data)
