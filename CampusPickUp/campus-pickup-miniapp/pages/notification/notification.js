const { request } = require('../../utils/request')
const { ensureComplete } = require('../../utils/auth')

Page({
  data: { list: [] },

  onShow() {
    ensureComplete().then((ok) => {
      if (ok) this.loadList()
    })
  },

  loadList() {
    request({ url: '/notification/my' })
      .then((res) => this.setData({ list: res.data || [] }))
      .catch(() => {})
  },

  markRead(e) {
    const id = Number(e.currentTarget.dataset.id)
    request({ url: '/notification/read/' + id, method: 'PUT' })
      .then(() => this.loadList())
      .catch(() => {})
  }
})
