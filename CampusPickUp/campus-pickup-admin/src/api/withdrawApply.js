import request from '@/utils/request'

export function listWithdrawApply(query) { return request({ url: '/order/withdraw/apply/list', method: 'get', params: query }) }
export function auditWithdrawApply(applyId, data) { return request({ url: '/order/withdraw/apply/audit/' + applyId, method: 'put', data }) }
