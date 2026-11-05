import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

/**
 * 路由配置。meta.roles 为空表示所有登录用户可见。
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/user/index.vue'),
        meta: { title: '用户管理', icon: 'User', roles: ['operation_admin'] }
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/role/index.vue'),
        meta: { title: '角色管理', icon: 'UserFilled', roles: ['super_admin'] }
      }
    ]
  },
  {
    path: '/biz',
    component: Layout,
    redirect: '/biz/order',
    meta: { title: '业务管理', icon: 'List' },
    children: [
      {
        path: 'order',
        name: 'Order',
        component: () => import('@/views/biz/order/index.vue'),
        meta: { title: '订单管理', icon: 'Document', roles: ['operation_admin', 'cs_admin'] }
      },
      {
        path: 'complaint',
        name: 'Complaint',
        component: () => import('@/views/biz/complaint/index.vue'),
        meta: { title: '申诉投诉', icon: 'ChatDotRound', roles: ['cs_admin', 'operation_admin'] }
      },
      {
        path: 'evaluate',
        name: 'Evaluate',
        component: () => import('@/views/biz/evaluate/index.vue'),
        meta: { title: '评价管理', icon: 'Star', roles: ['cs_admin', 'operation_admin'] }
      },
      {
        path: 'credit',
        name: 'Credit',
        component: () => import('@/views/biz/credit/index.vue'),
        meta: { title: '信誉分管理', icon: 'Medal', roles: ['cs_admin', 'operation_admin'] }
      },
      {
        path: 'creditApply',
        name: 'CreditApply',
        component: () => import('@/views/biz/creditApply/index.vue'),
        meta: { title: '信誉分申请', icon: 'Stamp', roles: ['super_admin', 'operation_admin', 'cs_admin'] }
      },
      {
        path: 'finishApply',
        name: 'FinishApply',
        component: () => import('@/views/biz/finishApply/index.vue'),
        meta: { title: '配送完成申请', icon: 'CircleCheck', roles: ['super_admin', 'operation_admin'] }
      },
      {
        path: 'withdrawApply',
        name: 'WithdrawApply',
        component: () => import('@/views/biz/withdrawApply/index.vue'),
        meta: { title: '接单撤回申请', icon: 'RefreshLeft', roles: ['super_admin', 'operation_admin', 'cs_admin'] }
      }
    ]
  },
  {
    path: '/content',
    component: Layout,
    redirect: '/content/notice',
    meta: { title: '内容管理', icon: 'Picture' },
    children: [
      {
        path: 'notice',
        name: 'Notice',
        component: () => import('@/views/content/notice/index.vue'),
        meta: { title: '公告管理', icon: 'Bell', roles: ['operation_admin'] }
      },
      {
        path: 'banner',
        name: 'Banner',
        component: () => import('@/views/content/banner/index.vue'),
        meta: { title: '轮播图管理', icon: 'PictureFilled', roles: ['operation_admin'] }
      }
    ]
  },
  {
    path: '/base',
    component: Layout,
    redirect: '/base/point',
    meta: { title: '基础数据', icon: 'Coin' },
    children: [
      {
        path: 'point',
        name: 'Point',
        component: () => import('@/views/base/point/index.vue'),
        meta: { title: '驿站管理', icon: 'Location', roles: ['base_admin'] }
      },
      {
        path: 'dormitory',
        name: 'Dormitory',
        component: () => import('@/views/base/dormitory/index.vue'),
        meta: { title: '宿舍楼管理', icon: 'OfficeBuilding', roles: ['base_admin'] }
      },
      {
        path: 'size',
        name: 'Size',
        component: () => import('@/views/base/size/index.vue'),
        meta: { title: '快递大小分类', icon: 'Box', roles: ['base_admin'] }
      },
      {
        path: 'school',
        name: 'School',
        component: () => import('@/views/base/school/index.vue'),
        meta: { title: '学校管理', icon: 'School', roles: ['base_admin'] }
      },
      {
        path: 'dict',
        name: 'Dict',
        component: () => import('@/views/base/dict/index.vue'),
        meta: { title: '数据字典', icon: 'Menu', roles: ['base_admin'] }
      }
    ]
  },
  {
    path: '/404',
    component: () => import('@/views/error/404.vue'),
    hidden: true
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404',
    hidden: true
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  }
})

export default router
