import request from '@/utils/request'

export function listDormitory(query) { return request({ url: '/system/dormitory/list', method: 'get', params: query }) }
export function optionselectDormitory() { return request({ url: '/system/dormitory/optionselect', method: 'get' }) }
export function getDormitory(id) { return request({ url: '/system/dormitory/' + id, method: 'get' }) }
export function addDormitory(data) { return request({ url: '/system/dormitory', method: 'post', data }) }
export function updateDormitory(data) { return request({ url: '/system/dormitory', method: 'put', data }) }
export function delDormitory(id) { return request({ url: '/system/dormitory/' + id, method: 'delete' }) }
