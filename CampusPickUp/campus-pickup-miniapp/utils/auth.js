const { request } = require('./request')

/**
 * 信息完善守卫：未登录返回 false；已登录但信息未完善时提示并跳转完善页。
 * 返回 Promise<boolean>，true 表示可以继续使用功能。
 */
function ensureComplete() {
  return new Promise((resolve) => {
    const token = wx.getStorageSync('token')
    if (!token) {
      resolve(false)
      return
    }
    request({ url: '/system/user/checkComplete' })
      .then((res) => {
        if (res.complete) {
          resolve(true)
        } else {
          wx.showToast({ title: res.message || '请先完善信息', icon: 'none', duration: 3000 })
          setTimeout(() => {
            wx.navigateTo({ url: '/pages/complete/complete' })
          }, 600)
          resolve(false)
        }
      })
      .catch(() => resolve(false))
  })
}

module.exports = { ensureComplete }
