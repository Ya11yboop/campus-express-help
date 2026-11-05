import request from '@/utils/request'

export function listOrder(query) { return request({ url: '/order/list', method: 'get', params: query }) }
export function getOrder(orderId) { return request({ url: '/order/' + orderId, method: 'get' }) }
export function createOrder(data) { return request({ url: '/order', method: 'post', data }) }
export function updateOrder(data) { return request({ url: '/order', method: 'put', data }) }
export function acceptOrder(orderId) { return request({ url: '/order/accept/' + orderId, method: 'put' }) }
export function finishOrder(orderId) { return request({ url: '/order/finish/' + orderId, method: 'put' }) }
export function cancelOrder(orderId) { return request({ url: '/order/cancel/' + orderId, method: 'put' }) }
export function delOrder(orderId) { return request({ url: '/order/' + orderId, method: 'delete' }) }
