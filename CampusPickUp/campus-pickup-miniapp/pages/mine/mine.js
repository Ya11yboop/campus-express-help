const { request } = require('../../utils/request')

Page({
  data: {
    user: null,
    hasToken: false,
    initial: '?',
    schoolName: '',
    unreadCount: 0
  },

  onShow() {
    const token = wx.getStorageSync('token')
    this.setData({ hasToken: !!token })
    if (token) {
      this.loadProfile()
      this.loadUnreadCount()
    }
  },

  loadProfile() {
    request({ url: '/system/user/profileInfo' })
      .then((res) => {
        const user = res.data || null
        const name = (user && (user.realName || user.nickName || user.userName)) || ''
        this.setData({
          user,
          initial: name ? name.charAt(0) : '?'
        })
        if (user && user.schoolId) this.loadSchoolName(user.schoolId)
      })
      .catch(() => {})
  },

  loadSchoolName(schoolId) {
    request({ url: '/system/school/optionselect' })
      .then((res) => {
        const schools = res.data || []
        const s = schools.find((x) => x.schoolId === schoolId)
        if (s) this.setData({ schoolName: s.schoolName })
      })
      .catch(() => {})
  },

  goLogin() {
    wx.navigateTo({ url: '/pages/login/login' })
  },

  goMyOrders() {
    wx.navigateTo({ url: '/pages/my-order/my-order' })
  },

  goAddress() {
    wx.navigateTo({ url: '/pages/address/address' })
  },

  goFeedback() {
    wx.navigateTo({ url: '/pages/feedback/feedback' })
  },

  goNotification() {
    wx.navigateTo({ url: '/pages/notification/notification' })
  },

  loadUnreadCount() {
    request({ url: '/notification/unreadCount' })
      .then((res) => this.setData({ unreadCount: res.data || 0 }))
      .catch(() => {})
  },

  handleLogout() {
    request({ url: '/logout', method: 'POST' })
      .catch(() => {})
      .finally(() => {
        wx.removeStorageSync('token')
        getApp().globalData.token = ''
        this.setData({ user: null, hasToken: false, initial: '?', schoolName: '' })
      })
  }
})
