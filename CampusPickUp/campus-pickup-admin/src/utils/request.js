import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getToken } from '@/utils/auth'

// 创建 axios 实例
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 10000
})

// 请求拦截器：携带 token
service.interceptors.request.use(
  (config) => {
    const token = getToken()
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器：统一处理返回结果
service.interceptors.response.use(
  (response) => {
    const res = response.data
    const code = res.code

    if (code === 200) {
      return res
    }

    if (code === 401) {
      ElMessageBox.confirm('登录状态已过期，请重新登录', '系统提示', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        window.location.href = '/login'
      })
      return Promise.reject(new Error(res.msg || '登录已过期'))
    }

    ElMessage.error(res.msg || '系统错误')
    return Promise.reject(new Error(res.msg || 'Error'))
  },
  (error) => {
    ElMessage.error(error.message || '请求失败')
    return Promise.reject(error)
  }
)

export default service
