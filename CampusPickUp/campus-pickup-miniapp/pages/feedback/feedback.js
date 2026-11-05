const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

const complaintTypeMap = { '1': '申诉', '2': '投诉' }
const complaintStatusMap = { '0': '待处理', '1': '已处理' }

Page({
  data: {
    tab: 'complaint',
    complaints: [],
    evaluations: []
  },

  onShow() {
    ensureComplete().then((ok) => {
      if (ok) this.loadAll()
    })
  },

  loadAll() {
    request({ url: '/complaint/my' })
      .then((res) => {
        const complaints = (res.data || []).map((c) => ({
          ...c,
          typeText: complaintTypeMap[c.complaintType] || c.complaintType,
          statusText: complaintStatusMap[c.handleStatus] || c.handleStatus
        }))
        this.setData({ complaints })
      })
      .catch(() => {})
    request({ url: '/evaluate/my' })
      .then((res) => this.setData({ evaluations: res.data || [] }))
      .catch(() => {})
  },

  switchTab(e) {
    this.setData({ tab: e.currentTarget.dataset.tab })
  }
})
