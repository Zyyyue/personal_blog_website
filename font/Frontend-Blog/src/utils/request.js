import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  timeout: 15000
})

http.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('blog_token')
    if (token) {
      config.headers = config.headers || {}
      config.headers['Authorization'] = token
    }
    return config
  },
  (error) => Promise.reject(error)
)

http.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('blog_token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default http
