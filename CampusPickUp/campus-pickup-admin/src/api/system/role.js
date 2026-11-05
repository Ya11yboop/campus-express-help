import request from '@/utils/request'

export function listRole(query) { return request({ url: '/system/role/list', method: 'get', params: query }) }
export function getRole(roleId) { return request({ url: '/system/role/' + roleId, method: 'get' }) }
export function addRole(data) { return request({ url: '/system/role', method: 'post', data }) }
export function updateRole(data) { return request({ url: '/system/role', method: 'put', data }) }
export function delRole(roleId) { return request({ url: '/system/role/' + roleId, method: 'delete' }) }
export function optionselectRole() { return request({ url: '/system/role/optionselect', method: 'get' }) }
export function getUserRoles(userId) { return request({ url: '/system/role/user/' + userId, method: 'get' }) }
export function assignUserRoles(userId, roleIds) { return request({ url: '/system/role/user/' + userId, method: 'put', data: roleIds }) }
