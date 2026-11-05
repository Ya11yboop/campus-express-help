const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

const statusMap = { 0: '待接单', 1: '配送中', 2: '已完成', 3: '已取消', 4: '申诉中' }
const typeMap = { 1: '帮取', 2: '帮寄' }

Page({
  data: {
    tab: 'my',
    myList: [],
    acceptedList: []
  },

  onShow() {
    ensureComplete().then((ok) => {
      if (ok) this.loadAll()
    })
  },

  loadAll() {
    request({ url: '/order/my' })
      .then((res) => this.setData({ myList: this.decorate(res.data || []) }))
      .catch(() => {})
    request({ url: '/order/accepted' })
      .then((res) => this.setData({ acceptedList: this.decorate(res.data || []) }))
      .catch(() => {})
  },

  decorate(list) {
    return list.map((o) => ({
      ...o,
      statusText: statusMap[o.orderStatus] || o.orderStatus,
      typeText: typeMap[o.orderType] || o.orderType
    }))
  },

  switchTab(e) {
    this.setData({ tab: e.currentTarget.dataset.tab })
  },

  goDetail(e) {
    wx.navigateTo({ url: '/pages/detail/detail?id=' + e.currentTarget.dataset.id })
  }
})
