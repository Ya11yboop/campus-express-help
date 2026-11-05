import request from '@/utils/request'

export function listCreditApply(query) { return request({ url: '/credit/apply/list', method: 'get', params: query }) }
export function addCreditApply(data) { return request({ url: '/credit/apply', method: 'post', data }) }
export function auditCreditApply(applyId, data) { return request({ url: '/credit/apply/audit/' + applyId, method: 'put', data }) }
