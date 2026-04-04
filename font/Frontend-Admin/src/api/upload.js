import http from '@/utils/request'

/**
 * 上传文件
 * @param {File} file
 */
export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return http.post('/admin/upload/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
