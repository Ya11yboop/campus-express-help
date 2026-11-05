const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

const statusMap = { 0: '待接单', 1: '配送中', 2: '已完成', 3: '已取消', 4: '申诉中' }
const typeMap = { 1: '帮取', 2: '帮寄' }

Page({
  data: {
    list: [],
    points: [],
    selectedPointName: '',
    feeMin: '',
    feeMax: '',
    deliveryAddress: '',
    pointId: null
  },

  onShow() {
    ensureComplete().then((ok) => {
      if (ok) {
        this.loadPoints()
        this.getList()
      }
    })
  },

  loadPoints() {
    request({ url: '/system/point/optionselect' })
      .then((res) => this.setData({ points: res.data || [] }))
      .catch(() => {})
  },

  getList() {
    const params = {}
    if (this.data.feeMin) params.feeMin = this.data.feeMin
    if (this.data.feeMax) params.feeMax = this.data.feeMax
    if (this.data.pointId) params.pointId = this.data.pointId
    if (this.data.deliveryAddress) params.deliveryAddress = this.data.deliveryAddress
    request({ url: '/order/hall', method: 'GET', data: params })
      .then((res) => {
        const list = (res.data || []).map((o) => ({
          ...o,
          statusText: statusMap[o.orderStatus] || o.orderStatus,
          typeText: typeMap[o.orderType] || o.orderType
        }))
        this.setData({ list })
      })
      .catch(() => {})
  },

  onFeeMin(e) { this.setData({ feeMin: e.detail.value }) },
  onFeeMax(e) { this.setData({ feeMax: e.detail.value }) },
  onDelivery(e) { this.setData({ deliveryAddress: e.detail.value }) },

  onSelectPoint(e) {
    const idx = Number(e.detail.value)
    const p = this.data.points[idx]
    this.setData({ pointId: p ? p.pointId : null, selectedPointName: p ? p.pointName : '' })
  },

  handleSearch() { this.getList() },

  resetSearch() {
    this.setData({ feeMin: '', feeMax: '', deliveryAddress: '', pointId: null, selectedPointName: '' })
    this.getList()
  },

  goDetail(e) {
    wx.navigateTo({ url: '/pages/detail/detail?id=' + e.currentTarget.dataset.id })
  }
})
