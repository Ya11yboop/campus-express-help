import request from '@/utils/request'

export function listCredit(query) { return request({ url: '/credit/list', method: 'get', params: query }) }
export function changeCredit(data) { return request({ url: '/credit/change', method: 'put', data }) }
