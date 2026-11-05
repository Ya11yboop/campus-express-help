const { request } = require('../../utils/request')

Page({
  data: {
    username: '',
    password: '',
    loading: false,
    wechatLoading: false
  },

  onUsername(e) {
    this.setData({ username: e.detail.value })
  },

  onPassword(e) {
    this.setData({ password: e.detail.value })
  },

  handleLogin() {
    const { username, password } = this.data
    if (!username || !password) {
      wx.showToast({ title: '请输入账号和密码', icon: 'none' })
      return
    }
    this.setData({ loading: true })
    request({
      url: '/login',
      method: 'POST',
      data: { username, password }
    })
      .then((res) => {
        wx.setStorageSync('token', res.token)
        getApp().globalData.token = res.token
        wx.showToast({ title: '登录成功', icon: 'success' })
        setTimeout(() => wx.navigateBack(), 800)
      })
      .catch(() => {})
      .finally(() => this.setData({ loading: false }))
  },

  wechatLogin() {
    this.setData({ wechatLoading: true })
    wx.login({
      success: (r) => {
        if (!r.code) {
          this.setData({ wechatLoading: false })
          wx.showToast({ title: '获取微信登录凭证失败', icon: 'none' })
          return
        }
        request({ url: '/login/wechat', method: 'POST', data: { code: r.code } })
          .then((res) => {
            wx.setStorageSync('token', res.token)
            getApp().globalData.token = res.token
            if (res.needComplete) {
              wx.redirectTo({ url: '/pages/complete/complete' })
            } else {
              wx.showToast({ title: '登录成功', icon: 'success' })
              setTimeout(() => wx.reLaunch({ url: '/pages/index/index' }), 800)
            }
          })
          .catch(() => {})
          .finally(() => this.setData({ wechatLoading: false }))
      },
      fail: () => {
        this.setData({ wechatLoading: false })
        wx.showToast({ title: '微信登录失败', icon: 'none' })
      }
    })
  }
})
