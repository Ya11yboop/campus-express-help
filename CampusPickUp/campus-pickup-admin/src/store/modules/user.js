import { defineStore } from 'pinia'
import { login, logout } from '@/api/login'
import { getProfile } from '@/api/system/user'
import { getToken, setToken, removeToken } from '@/utils/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    mySchoolId: null,       // 校区管理员绑定的学校（写死）
    globalSchoolId: localStorage.getItem('globalSchoolId')
      ? Number(localStorage.getItem('globalSchoolId'))
      : null   // 全局选中的学校（持久化，超管切换）
  }),
  getters: {
    isCampusAdmin: (state) => state.roles.some((r) => r.startsWith('campus_')),
    // 当前生效的学校过滤：校区管理员强制本校，否则用全局选中学校
    activeSchoolId: (state) =>
      state.roles.some((r) => r.startsWith('campus_')) ? state.mySchoolId : state.globalSchoolId
  },
  actions: {
    // 设置全局学校并持久化
    setGlobalSchool(id) {
      this.globalSchoolId = id || null
      if (this.globalSchoolId == null) {
        localStorage.removeItem('globalSchoolId')
      } else {
        localStorage.setItem('globalSchoolId', this.globalSchoolId)
      }
    },
    // 登录
    login(userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo.username, userInfo.password)
          .then((res) => {
            setToken(res.token)
            this.token = res.token
            this.roles = res.roles || []
            resolve(res)
          })
          .catch((err) => reject(err))
      })
    },

    // 获取当前用户信息
    getInfo() {
      return new Promise((resolve, reject) => {
        getProfile()
          .then((res) => {
            if (res.data) {
              this.name = res.data.username || ''
              this.roles = res.data.roles || []
              this.mySchoolId = res.data.schoolId || null
            }
            resolve(res)
          })
          .catch((err) => reject(err))
      })
    },

    // 退出登录
    logout() {
      return new Promise((resolve) => {
        logout()
          .catch(() => {})
          .finally(() => {
            this.token = ''
            this.name = ''
            this.roles = []
            this.mySchoolId = null
            this.globalSchoolId = null
            localStorage.removeItem('globalSchoolId')
            removeToken()
            resolve()
          })
      })
    }
  }
})
