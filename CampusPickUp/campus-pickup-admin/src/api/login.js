import request from '@/utils/request'

// 登录
export function login(username, password, code, uuid) {
  return request({
    url: '/login',
    method: 'post',
    data: { username, password, code, uuid }
  })
}

// 退出登录
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}
