import router from './router'
import { useUserStore } from '@/store/modules/user'
import { getToken } from '@/utils/auth'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

NProgress.configure({ showSpinner: false })

// 白名单（无需登录即可访问）
const whiteList = ['/login']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      const userStore = useUserStore()
      if (!userStore.name) {
        // 拉取当前用户信息（含角色）
        userStore
          .getInfo()
          .then(() => next())
          .catch(() => {
            userStore.logout().then(() => next('/login'))
          })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
