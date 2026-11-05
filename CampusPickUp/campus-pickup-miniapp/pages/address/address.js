const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

Page({
  data: {
    list: [],
    showForm: false,
    form: {},
    selectedId: null
  },

  onShow() {
    ensureComplete().then((ok) => {
      if (!ok) return
      this.setData({ selectedId: Number(wx.getStorageSync('currentAddressId')) || null })
      this.loadList()
    })
  },

  loadList() {
    request({ url: '/system/address/list' })
      .then((res) => this.setData({ list: res.data || [] }))
      .catch(() => {})
  },

  // 点击地址卡片切换当前使用地址，并返回上一个页面（下订单界面）
  selectAddr(e) {
    const id = Number(e.currentTarget.dataset.id)
    wx.setStorageSync('currentAddressId', id)
    this.setData({ selectedId: id })
    wx.navigateBack()
  },

  // 设为默认地址
  setDefault(e) {
    const id = Number(e.currentTarget.dataset.id)
    request({ url: '/system/address/default/' + id, method: 'PUT' })
      .then(() => {
        wx.showToast({ title: '已设为默认', icon: 'success' })
        this.loadList()
      })
      .catch(() => {})
  },

  openForm() {
    this.setData({ showForm: true, form: {} })
  },

  closeForm() {
    this.setData({ showForm: false })
  },

  editAddr(e) {
    const id = Number(e.currentTarget.dataset.id)
    const addr = this.data.list.find((a) => a.addressId === id)
    this.setData({ showForm: true, form: { ...addr } })
  },

  onInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({ [`form.${field}`]: e.detail.value })
  },

  saveAddr() {
    const form = this.data.form
    if (!form.contactName || !form.contactPhone || !form.address) {
      wx.showToast({ title: '请填写完整信息', icon: 'none' })
      return
    }
    const isEdit = !!form.addressId
    request({
      url: '/system/address',
      method: isEdit ? 'PUT' : 'POST',
      data: form
    })
      .then(() => {
        wx.showToast({ title: '保存成功', icon: 'success' })
        this.setData({ showForm: false })
        this.loadList()
      })
      .catch(() => {})
  },

  delAddr(e) {
    const id = Number(e.currentTarget.dataset.id)
    wx.showModal({ title: '提示', content: '确定删除该地址吗？' })
      .then((res) => {
        if (res.confirm) {
          request({ url: '/system/address/' + id, method: 'DELETE' })
            .then(() => {
              if (this.data.selectedId === id) {
                wx.removeStorageSync('currentAddressId')
                this.setData({ selectedId: null })
              }
              wx.showToast({ title: '删除成功', icon: 'success' })
              this.loadList()
            })
            .catch(() => {})
        }
      })
  }
})
