import request from '@/utils/request'

// 查询用户列表（分页）
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 用户下拉选项（客服/运营/超管选择用户）
export function optionselectUser() {
  return request({
    url: '/system/user/optionselect',
    method: 'get'
  })
}

// 查询用户详情
export function getUser(userId) {
  return request({
    url: '/system/user/' + userId,
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/system/user/' + userId,
    method: 'delete'
  })
}

// 获取当前登录用户信息
export function getProfile() {
  return request({
    url: '/system/user/profile',
    method: 'get'
  })
}
