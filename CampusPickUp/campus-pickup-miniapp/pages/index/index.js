const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

Page({
  data: {
    banners: [],
    notices: [],
    hasToken: false,
    currentIndex: 0,
    unreadCount: 0
  },

  onShow() {
    this.setData({ hasToken: !!wx.getStorageSync('token') })
    this.loadBanners()
    this.loadNotices()
    this.loadUnreadCount()
    // 信息未完善则提示并跳转完善页
    ensureComplete()
  },

  onSwiperChange(e) {
    this.setData({ currentIndex: e.detail.current })
  },

  onDotTap(e) {
    this.setData({ currentIndex: Number(e.currentTarget.dataset.index) })
  },

  loadBanners() {
    request({ url: '/banner/listAll' })
      .then((res) => this.setData({ banners: res.data || [] }))
      .catch(() => {})
  },

  loadNotices() {
    request({ url: '/notice/listAll' })
      .then((res) => this.setData({ notices: res.data || [] }))
      .catch(() => {})
  },

  loadUnreadCount() {
    if (!this.data.hasToken) return
    request({ url: '/notification/unreadCount' })
      .then((res) => this.setData({ unreadCount: res.data || 0 }))
      .catch(() => {})
  },

  goNotification() {
    if (!this.data.hasToken) {
      wx.navigateTo({ url: '/pages/login/login' })
      return
    }
    wx.navigateTo({ url: '/pages/notification/notification' })
  },

  goCreate(e) {
    const type = e.currentTarget.dataset.type
    if (!this.data.hasToken) {
      wx.navigateTo({ url: '/pages/login/login' })
      return
    }
    ensureComplete().then((ok) => {
      if (ok) wx.navigateTo({ url: '/pages/create/create?type=' + type })
    })
  }
})
