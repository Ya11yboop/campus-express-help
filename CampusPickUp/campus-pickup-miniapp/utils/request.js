/**
 * 网络请求封装：统一携带 token，统一处理返回结果
 */
function request(options) {
  const app = getApp()
  const token = app.globalData.token || wx.getStorageSync('token') || ''

  return new Promise((resolve, reject) => {
    wx.request({
      url: app.globalData.baseUrl + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        ...(token ? { Authorization: 'Bearer ' + token } : {})
      },
      success(res) {
        if (res.statusCode === 401) {
          wx.removeStorageSync('token')
          app.globalData.token = ''
          wx.navigateTo({ url: '/pages/login/login' })
          reject(res.data)
        } else if (res.data && res.data.code === 200) {
          resolve(res.data)
        } else {
          wx.showToast({ title: (res.data && res.data.msg) || '请求失败', icon: 'none' })
          reject(res.data)
        }
      },
      fail(err) {
        wx.showToast({ title: '网络错误，请检查后端是否启动', icon: 'none' })
        reject(err)
      }
    })
  })
}

module.exports = { request }
