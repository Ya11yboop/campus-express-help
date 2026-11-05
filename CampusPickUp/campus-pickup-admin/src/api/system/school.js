import request from '@/utils/request'

export function listSchool(query) { return request({ url: '/system/school/list', method: 'get', params: query }) }
export function optionselectSchool() { return request({ url: '/system/school/optionselect', method: 'get' }) }
export function getSchool(id) { return request({ url: '/system/school/' + id, method: 'get' }) }
export function addSchool(data) { return request({ url: '/system/school', method: 'post', data }) }
export function updateSchool(data) { return request({ url: '/system/school', method: 'put', data }) }
export function delSchool(ids) { return request({ url: '/system/school/' + ids, method: 'delete' }) }
