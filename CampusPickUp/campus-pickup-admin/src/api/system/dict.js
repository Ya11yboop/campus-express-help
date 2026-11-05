import request from '@/utils/request'

// 字典类型
export function listDictType(query) { return request({ url: '/system/dict/type/list', method: 'get', params: query }) }
export function getDictType(dictId) { return request({ url: '/system/dict/type/' + dictId, method: 'get' }) }
export function addDictType(data) { return request({ url: '/system/dict/type', method: 'post', data }) }
export function updateDictType(data) { return request({ url: '/system/dict/type', method: 'put', data }) }
export function delDictType(dictId) { return request({ url: '/system/dict/type/' + dictId, method: 'delete' }) }

// 字典数据
export function listDictData(query) { return request({ url: '/system/dict/data/list', method: 'get', params: query }) }
export function getDictData(dictCode) { return request({ url: '/system/dict/data/' + dictCode, method: 'get' }) }
export function addDictData(data) { return request({ url: '/system/dict/data', method: 'post', data }) }
export function updateDictData(data) { return request({ url: '/system/dict/data', method: 'put', data }) }
export function delDictData(dictCode) { return request({ url: '/system/dict/data/' + dictCode, method: 'delete' }) }
