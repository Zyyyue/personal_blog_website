import http from '@/utils/request'

/**
 * 获取评论列表
 */
export const getCommentPage = (articleId, page, pageSize) =>
  http.get('/comment/page', { params: { articleId, page, pageSize } })

/**
 * 提交评论
 */
export const submitComment = (data) => http.post('/comment', data)
