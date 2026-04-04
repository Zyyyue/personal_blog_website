import http from '@/utils/request'

/**
 * 根据文章 ID 获取评论列表
 * @param {number} articleId
 */
export const getCommentsByArticleId = (articleId) =>
  http.get(`/admin/article/comment/${articleId}`)

/**
 * 分页查询评论
 * @param {{ page: number, pageSize: number, articleId?: number, isApproved?: number }} params
 */
export const getCommentPage = (params) => {
  // 过滤掉空值参数
  const filteredParams = {}
  if (params.page) filteredParams.page = params.page
  if (params.pageSize) filteredParams.pageSize = params.pageSize
  if (params.articleId) filteredParams.articleId = params.articleId
  if (params.isApproved !== undefined && params.isApproved !== null && params.isApproved !== '') {
    filteredParams.isApproved = params.isApproved
  }
  return http.get('/admin/article/comment/page', { params: filteredParams })
}

/**
 * 批量审核通过评论
 * @param {number[]} ids
 */
export const approveComments = (ids) =>
  http.put('/admin/article/comment/approve', null, {
    params: { ids: ids.join(',') }
  })

/**
 * 批量删除评论
 * @param {number[]} ids
 */
export const deleteComments = (ids) =>
  http.delete('/admin/article/comment', { params: { ids: ids.join(',') } })

/**
 * 管理员回复评论
 * @param {object} data
 */
export const replyComment = (data) =>
  http.post('/admin/article/comment/reply', data)
