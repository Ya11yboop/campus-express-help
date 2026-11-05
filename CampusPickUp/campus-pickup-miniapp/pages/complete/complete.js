const { request } = require('../../utils/request')

Page({
  data: {
    schools: [],
    schoolIndex: -1,
    form: {
      userName: '',
      password: '',
      realName: '',
      phone: '',
      schoolId: null,
      studentNo: '',
      idCard: ''
    }
  },

  onLoad() {
    this.loadSchools()
  },

  loadSchools() {
    request({ url: '/system/school/optionselect' })
      .then((res) => this.setData({ schools: res.data || [] }))
      .catch(() => {})
  },

  onInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({ [`form.${field}`]: e.detail.value })
  },

  onSelectSchool(e) {
    const idx = Number(e.detail.value)
    const s = this.data.schools[idx]
    this.setData({ schoolIndex: idx, 'form.schoolId': s ? s.schoolId : null })
  },

  submit() {
    const f = this.data.form
    if (!f.userName) return this.toast('请填写用户名')
    if (!f.password) return this.toast('请填写密码')
    if (!f.realName) return this.toast('请填写姓名')
    if (!f.phone) return this.toast('请填写电话')
    if (!f.schoolId) return this.toast('请选择学校')
    if (!f.studentNo) return this.toast('请填写学号')
    if (!f.idCard) return this.toast('请填写身份证')

    request({ url: '/system/user/complete', method: 'POST', data: f })
      .then(() => {
        wx.showToast({ title: '完善成功', icon: 'success' })
        setTimeout(() => wx.reLaunch({ url: '/pages/index/index' }), 800)
      })
      .catch(() => {})
  },

  toast(title) {
    wx.showToast({ title, icon: 'none' })
  }
})
