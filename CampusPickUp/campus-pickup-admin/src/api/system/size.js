import request from '@/utils/request'

export function listSize(query) { return request({ url: '/system/size/list', method: 'get', params: query }) }
export function optionselectSize() { return request({ url: '/system/size/optionselect', method: 'get' }) }
export function getSize(sizeId) { return request({ url: '/system/size/' + sizeId, method: 'get' }) }
export function addSize(data) { return request({ url: '/system/size', method: 'post', data }) }
export function updateSize(data) { return request({ url: '/system/size', method: 'put', data }) }
export function delSize(sizeId) { return request({ url: '/system/size/' + sizeId, method: 'delete' }) }
