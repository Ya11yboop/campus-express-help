import request from '@/utils/request'

export function listPoint(query) { return request({ url: '/system/point/list', method: 'get', params: query }) }
export function optionselectPoint() { return request({ url: '/system/point/optionselect', method: 'get' }) }
export function getPoint(pointId) { return request({ url: '/system/point/' + pointId, method: 'get' }) }
export function addPoint(data) { return request({ url: '/system/point', method: 'post', data }) }
export function updatePoint(data) { return request({ url: '/system/point', method: 'put', data }) }
export function delPoint(pointId) { return request({ url: '/system/point/' + pointId, method: 'delete' }) }
