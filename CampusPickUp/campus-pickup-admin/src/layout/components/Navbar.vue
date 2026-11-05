<template>
  <div class="navbar">
    <div class="navbar-left">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="index">
          <span :class="{ 'is-current': index === breadcrumbs.length - 1 }">{{ item.title }}</span>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="navbar-right">
      <el-select
        v-model="schoolId"
        placeholder="选择学校"
        clearable
        filterable
        :disabled="userStore.isCampusAdmin"
        style="width: 170px; margin-right: 16px"
        @change="handleSchoolChange"
      >
        <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
      </el-select>
      <el-dropdown @command="handleCommand">
        <span class="user-info">
          <span class="user-avatar"><el-icon><UserFilled /></el-icon></span>
          <span class="user-name">{{ userStore.name || '管理员' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import { optionselectSchool } from '@/api/system/school'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const currentTitle = computed(() => route.meta.title || '')

const groupMap = {
  '/system': '系统管理',
  '/biz': '业务管理',
  '/content': '内容管理',
  '/base': '基础数据'
}

const breadcrumbs = computed(() => {
  const items = [{ title: '首页' }]
  const path = route.path
  if (path === '/index' || path === '/') {
    return items
  }
  const seg = '/' + (path.split('/')[1] || '')
  if (groupMap[seg]) {
    items.push({ title: groupMap[seg] })
  }
  if (route.meta.title) {
    items.push({ title: route.meta.title })
  }
  return items
})

const schoolId = ref(null)
const schoolList = ref([])

function loadSchool() {
  optionselectSchool().then((res) => {
    schoolList.value = res.data || []
  })
}

function initSchool() {
  // 校区管理员写死本校；其他管理员显示全局选中的学校
  schoolId.value = userStore.isCampusAdmin ? userStore.mySchoolId : userStore.globalSchoolId
}

function handleSchoolChange(val) {
  userStore.setGlobalSchool(val)
  window.location.reload()
}

function handleCommand(command) {
  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', { type: 'warning' })
      .then(() => {
        userStore.logout().then(() => router.push('/login'))
      })
      .catch(() => {})
  }
}

onMounted(() => {
  loadSchool()
  initSchool()
})
</script>

<style scoped>
.navbar {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.navbar-right {
  display: flex;
  align-items: center;
}

.page-title {
  font-size: 16px;
  font-weight: 500;
}

.el-breadcrumb {
  font-size: 15px;
}

.is-current {
  color: #1f2d3d;
  font-weight: 600;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: #333;
  outline: none;
  padding: 4px 10px;
  border-radius: 20px;
  transition: background 0.2s;
}

.user-info:hover {
  background: #f5f7fa;
}

.user-avatar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  color: #fff;
  background: linear-gradient(135deg, #409eff, #2a5298);
  font-size: 15px;
}

.user-name {
  font-size: 14px;
}
</style>
