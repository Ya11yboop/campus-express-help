const { request } = require('../../utils/request')

const statusMap = { 0: '待接单', 1: '配送中', 2: '已完成', 3: '已取消', 4: '申诉中' }
const typeMap = { 1: '帮取', 2: '帮寄' }

Page({
  data: {
    order: null,
    statusText: '',
    typeText: '',
    canAccept: false,
    canFinish: false,
    canCancel: false,
    canWithdraw: false,
    canEvaluate: false,
    showEval: false,
    evalScore: 5,
    evalContent: '',
    showComplaint: false,
    complaintType: '2',
    complaintContent: ''
  },

  onLoad(options) {
    this.orderId = options.id
  },

  onShow() {
    this.loadDetail()
  },

  loadDetail() {
    Promise.all([
      request({ url: '/order/' + this.orderId }),
      request({ url: '/system/user/profileInfo' })
    ])
      .then(([orderRes, profileRes]) => {
        const order = orderRes.data || {}
        const me = profileRes.data || {}
        const currentId = me.userId != null ? Number(me.userId) : null
        const orderUserId = order.userId != null ? Number(order.userId) : null
        const courierId = order.courierId != null ? Number(order.courierId) : null
        this.setData({
          order,
          statusText: statusMap[order.orderStatus] || order.orderStatus,
          typeText: typeMap[order.orderType] || order.orderType,
          canAccept: order.orderStatus === '0' && currentId != null && orderUserId !== currentId,
          canFinish: order.orderStatus === '1' && currentId != null && courierId === currentId,
          canCancel: order.orderStatus === '0' && currentId != null && orderUserId === currentId,
          canWithdraw: order.orderStatus === '1' && currentId != null && courierId === currentId,
          canEvaluate: order.orderStatus === '2'
        })
      })
      .catch(() => {})
  },

  accept() {
    request({ url: '/order/accept/' + this.orderId, method: 'PUT' })
      .then(() => { wx.showToast({ title: '接单成功', icon: 'success' }); this.loadDetail() })
      .catch(() => {})
  },

  applyFinish() {
    wx.showActionSheet({
      itemList: ['上传照片申请', '直接申请'],
      success: (res) => {
        if (res.tapIndex === 0) {
          this.chooseAndApply()
        } else {
          this.submitFinishApply('')
        }
      }
    })
  },

  chooseAndApply() {
    wx.chooseImage({
      count: 1,
      success: (res) => {
        const filePath = res.tempFilePaths[0]
        const token = wx.getStorageSync('token') || ''
        wx.uploadFile({
          url: getApp().globalData.baseUrl + '/upload/local',
          filePath,
          name: 'file',
          header: { Authorization: 'Bearer ' + token },
          success: (upRes) => {
            let data = {}
            try { data = JSON.parse(upRes.data) } catch (e) {}
            this.submitFinishApply(data.code === 200 ? data.url : '')
          },
          fail: () => this.submitFinishApply('')
        })
      }
    })
  },

  submitFinishApply(image) {
    request({ url: '/finish/apply', method: 'POST', data: { orderId: Number(this.orderId), image: image || '' } })
      .then(() => { wx.showToast({ title: '已提交申请', icon: 'success' }); this.loadDetail() })
      .catch(() => {})
  },

  cancel() {
    request({ url: '/order/cancel/' + this.orderId, method: 'PUT' })
      .then(() => { wx.showToast({ title: '已取消', icon: 'none' }); this.loadDetail() })
      .catch(() => {})
  },

  withdraw() {
    wx.showModal({
      title: '撤回接单',
      editable: true,
      placeholderText: '接单超30分钟需填写撤回理由（30分钟内可留空）',
      success: (r) => {
        if (r.confirm) {
          request({ url: '/order/withdraw/' + this.orderId, method: 'PUT', data: { reason: r.content || '' } })
            .then((res) => { wx.showToast({ title: res.msg || '操作成功', icon: 'none' }); this.loadDetail() })
            .catch(() => {})
        }
      }
    })
  },

  evaluate() {
    this.setData({ showEval: true, evalScore: 5, evalContent: '' })
  },

  onStarTap(e) {
    this.setData({ evalScore: Number(e.currentTarget.dataset.star) })
  },

  onEvalInput(e) {
    this.setData({ evalContent: e.detail.value })
  },

  closeEval() {
    this.setData({ showEval: false })
  },

  submitEval() {
    request({
      url: '/evaluate',
      method: 'POST',
      data: { orderId: Number(this.orderId), score: this.data.evalScore, content: this.data.evalContent }
    })
      .then(() => {
        wx.showToast({ title: '评价成功', icon: 'success' })
        this.setData({ showEval: false })
      })
      .catch(() => {})
  },

  complaint() {
    this.setData({ showComplaint: true, complaintType: '2', complaintContent: '' })
  },

  onComplaintType(e) {
    this.setData({ complaintType: e.currentTarget.dataset.type })
  },

  onComplaintInput(e) {
    this.setData({ complaintContent: e.detail.value })
  },

  closeComplaint() {
    this.setData({ showComplaint: false })
  },

  submitComplaint() {
    const content = this.data.complaintContent
    if (!content) {
      wx.showToast({ title: '请输入内容', icon: 'none' })
      return
    }
    request({
      url: '/complaint',
      method: 'POST',
      data: { orderId: Number(this.orderId), complaintType: this.data.complaintType, content, targetUserId: this.data.order.courierId }
    })
      .then(() => {
        wx.showToast({ title: '提交成功', icon: 'success' })
        this.setData({ showComplaint: false })
      })
      .catch(() => {})
  }
})
