import request from '@/utils/request'

export function listEvaluate(query) { return request({ url: '/evaluate/list', method: 'get', params: query }) }
export function getEvaluate(id) { return request({ url: '/evaluate/' + id, method: 'get' }) }
export function replyEvaluate(data) { return request({ url: '/evaluate/reply', method: 'put', data }) }
export function delEvaluate(id) { return request({ url: '/evaluate/' + id, method: 'delete' }) }
