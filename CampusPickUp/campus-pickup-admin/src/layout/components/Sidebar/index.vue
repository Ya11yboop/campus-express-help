<template>
  <div class="sidebar-container">
    <div class="logo">
      <el-icon class="logo-icon" :size="20"><Van /></el-icon>
      <span class="logo-title">校园快递配送系统</span>
    </div>
    <el-scrollbar>
      <el-menu
        :default-active="activeMenu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <sidebar-item
          v-for="route in menuRoutes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { constantRoutes } from '@/router'
import { useUserStore } from '@/store/modules/user'
import SidebarItem from './SidebarItem.vue'

const route = useRoute()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

function hasPerm(roles, required) {
  if (!required || required.length === 0) return true
  if (roles.includes('super_admin') || roles.includes('campus_admin')) return true
  return roles.some((r) => {
    if (required.includes(r)) return true
    if (r.startsWith('campus_')) return required.includes(r.replace('campus_', ''))
    return false
  })
}

// 根据角色过滤菜单
const menuRoutes = computed(() => {
  const result = []
  constantRoutes.forEach((route) => {
    if (route.hidden) return
    const tmp = { ...route }
    if (tmp.children) {
      tmp.children = tmp.children.filter(
        (child) => !child.hidden && hasPerm(userStore.roles, child.meta && child.meta.roles)
      )
      if (tmp.children.length > 0) {
        result.push(tmp)
      }
    } else if (hasPerm(userStore.roles, tmp.meta && tmp.meta.roles)) {
      result.push(tmp)
    }
  })
  return result
})
</script>

<style scoped>
.sidebar-container {
  width: 210px;
  height: 100%;
  background: #304156;
  overflow: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: #fff;
  font-size: 15px;
  font-weight: 600;
  background: #2b3a4a;
  white-space: nowrap;
  overflow: hidden;
}

.logo-icon {
  color: #409eff;
}

.el-menu {
  border-right: none;
}
</style>
