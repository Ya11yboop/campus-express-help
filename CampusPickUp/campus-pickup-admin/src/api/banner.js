import request from '@/utils/request'

export function listBanner(query) { return request({ url: '/banner/list', method: 'get', params: query }) }
export function getBanner(id) { return request({ url: '/banner/' + id, method: 'get' }) }
export function addBanner(data) { return request({ url: '/banner', method: 'post', data }) }
export function updateBanner(data) { return request({ url: '/banner', method: 'put', data }) }
export function delBanner(id) { return request({ url: '/banner/' + id, method: 'delete' }) }
