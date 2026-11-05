import request from '@/utils/request'

export function getStats() {
  return request({
    url: '/dashboard/stats',
    method: 'get'
  })
}

export function getTrend() {
  return request({
    url: '/dashboard/trend',
    method: 'get'
  })
}
