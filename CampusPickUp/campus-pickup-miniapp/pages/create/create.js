const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

Page({
  data: {
    type: '1',
    points: [],
    sizes: [],
    addresses: [],
    selectedPointName: '',
    selectedSizeName: '',
    selectedAddressText: '',
    form: {
      trackingNo: '',
      expressCompany: '',
      sizeId: null,
      pointId: null,
      deliveryAddress: '',
      receiverName: '',
      receiverPhone: '',
      fee: null,
      remark: ''
    }
  },

  onLoad(options) {
    if (options.type) this.setData({ type: options.type })
    this.loadOptions()
  },

  onShow() {
    ensureComplete().then((ok) => {
      // 从地址管理切换返回后，刷新选中地址
      if (ok) this.loadAddresses()
    })
  },

  loadOptions() {
    request({ url: '/system/point/optionselect' }).then((res) => this.setData({ points: res.data || [] })).catch(() => {})
    request({ url: '/system/size/optionselect' }).then((res) => this.setData({ sizes: res.data || [] })).catch(() => {})
  },

  loadAddresses() {
    request({ url: '/system/address/list' })
      .then((res) => {
        const addresses = res.data || []
        this.setData({ addresses })
        // 优先用切换选中的地址，否则用默认地址，再否则用第一条
        const currentId = Number(wx.getStorageSync('currentAddressId')) || null
        const cur = currentId
          ? addresses.find((a) => a.addressId === currentId)
          : (addresses.find((a) => a.isDefault === '1') || addresses[0])
        if (cur) this.applyAddress(cur)
      })
      .catch(() => {})
  },

  applyAddress(addr) {
    this.setData({
      selectedAddressText: addr.address,
      'form.deliveryAddress': addr.address,
      'form.receiverName': addr.contactName || '',
      'form.receiverPhone': addr.contactPhone || ''
    })
  },

  onInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({ [`form.${field}`]: e.detail.value })
  },

  onSelectPoint(e) {
    const idx = Number(e.detail.value)
    const p = this.data.points[idx]
    this.setData({ 'form.pointId': p ? p.pointId : null, selectedPointName: p ? p.pointName : '' })
  },

  onSelectSize(e) {
    const idx = Number(e.detail.value)
    const s = this.data.sizes[idx]
    this.setData({ 'form.sizeId': s ? s.sizeId : null, selectedSizeName: s ? s.sizeName : '' })
  },

  onSelectAddress(e) {
    const idx = Number(e.detail.value)
    const addr = this.data.addresses[idx]
    if (addr) this.applyAddress(addr)
  },

  goAddress() {
    wx.navigateTo({ url: '/pages/address/address' })
  },

  submit() {
    const form = this.data.form
    if (!form.deliveryAddress) {
      wx.showToast({ title: '请选择送达地址', icon: 'none' })
      return
    }
    request({ url: '/order', method: 'POST', data: { ...form, orderType: this.data.type } })
      .then(() => {
        wx.showToast({ title: '下单成功', icon: 'success' })
        setTimeout(() => wx.navigateBack(), 800)
      })
      .catch(() => {})
  }
})
