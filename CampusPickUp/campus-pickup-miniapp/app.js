// 校园快递配送系统 - 小程序全局逻辑
App({
  globalData: {
    // 后端接口地址（开发环境，可在需要时替换为真实域名）
    baseUrl: 'http://localhost:8080',
    token: ''
  },

  onLaunch() {
    // 读取本地缓存的登录令牌
    this.globalData.token = wx.getStorageSync('token') || ''
  }
})
