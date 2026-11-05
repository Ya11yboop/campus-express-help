import request from '@/utils/request'

export function listFinishApply(query) { return request({ url: '/finish/apply/list', method: 'get', params: query }) }
export function auditFinishApply(applyId, data) { return request({ url: '/finish/apply/audit/' + applyId, method: 'put', data }) }
