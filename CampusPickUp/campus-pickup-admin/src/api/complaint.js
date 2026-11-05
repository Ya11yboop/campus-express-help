import request from '@/utils/request'

export function listComplaint(query) { return request({ url: '/complaint/list', method: 'get', params: query }) }
export function getComplaint(id) { return request({ url: '/complaint/' + id, method: 'get' }) }
export function createComplaint(data) { return request({ url: '/complaint', method: 'post', data }) }
export function handleComplaint(data) { return request({ url: '/complaint/handle', method: 'put', data }) }
export function delComplaint(id) { return request({ url: '/complaint/' + id, method: 'delete' }) }
