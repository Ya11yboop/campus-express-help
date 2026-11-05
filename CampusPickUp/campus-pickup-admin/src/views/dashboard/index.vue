<template>
  <div class="dashboard">
    <!-- 欢迎横幅 -->
    <div class="welcome-banner">
      <div class="welcome-left">
        <div class="welcome-title">{{ greeting }}，{{ userName }}</div>
        <div class="welcome-sub">{{ today }} · 欢迎使用校园快递配送系统，祝您工作顺利</div>
      </div>
      <div class="welcome-right">
        <el-icon :size="58" class="welcome-icon"><Van /></el-icon>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="16" class="stat-row">
      <el-col :span="6" v-for="item in statItems" :key="item.label">
        <div class="stat-card" :style="{ background: item.gradient }">
          <div class="stat-icon">
            <el-icon :size="26"><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-body">
            <div class="stat-value">{{ item.value }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
          <svg v-if="item.line" class="spark" viewBox="0 0 80 26" preserveAspectRatio="none">
            <polygon :points="item.area" fill="rgba(255,255,255,0.18)" />
            <polyline :points="item.line" fill="none" stroke="rgba(255,255,255,0.92)" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round" />
          </svg>
        </div>
      </el-col>
    </el-row>

    <!-- 功能模块概览 -->
    <el-card shadow="never" class="module-card">
      <template #header>
        <div class="module-header">
          <span class="module-title">功能模块</span>
          <span class="module-sub">快速了解系统能力</span>
        </div>
      </template>
      <div class="module-grid">
        <div class="module-item" v-for="m in modules" :key="m.title">
          <div class="module-icon" :style="{ background: m.bg, color: m.color }">
            <el-icon :size="22"><component :is="m.icon" /></el-icon>
          </div>
          <div class="module-text">
            <div class="module-name">{{ m.title }}</div>
            <div class="module-desc">{{ m.desc }}</div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getStats, getTrend } from '@/api/dashboard'
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()
const stats = ref({})
const trend = ref({ orders: [], users: [], evaluates: [], complaints: [] })

const userName = computed(() => userStore.name || '管理员')

const greeting = computed(() => {
  const h = new Date().getHours()
  if (h < 6) return '夜深了'
  if (h < 9) return '早上好'
  if (h < 12) return '上午好'
  if (h < 14) return '中午好'
  if (h < 18) return '下午好'
  return '晚上好'
})

const today = computed(() => {
  const d = new Date()
  const week = ['日', '一', '二', '三', '四', '五', '六'][d.getDay()]
  return `${d.getFullYear()}年${d.getMonth() + 1}月${d.getDate()}日 星期${week}`
})

function buildSpark(values) {
  if (!values || values.length < 2) return { line: '', area: '' }
  const w = 80
  const h = 26
  let max = Math.max(...values)
  let min = Math.min(...values)
  if (max === min) { max += 1; min -= 1 }
  const range = max - min || 1
  const step = w / (values.length - 1)
  const pts = values.map((v, i) => {
    const x = (i * step).toFixed(1)
    const y = (h - ((v - min) / range) * (h - 4) - 2).toFixed(1)
    return `${x},${y}`
  })
  return { line: pts.join(' '), area: `0,${h} ${pts.join(' ')} ${w},${h}` }
}

const statItems = computed(() => [
  { label: '用户总数', value: stats.value.userCount ?? 0, icon: 'User', gradient: 'linear-gradient(135deg, #409eff, #2a5298)', ...buildSpark(trend.value.users) },
  { label: '订单总数', value: stats.value.orderCount ?? 0, icon: 'Document', gradient: 'linear-gradient(135deg, #f6a821, #f07c0f)', ...buildSpark(trend.value.orders) },
  { label: '评价总数', value: stats.value.evaluateCount ?? 0, icon: 'Star', gradient: 'linear-gradient(135deg, #36cfc9, #13a8a8)', ...buildSpark(trend.value.evaluates) },
  { label: '申诉投诉', value: stats.value.complaintCount ?? 0, icon: 'Warning', gradient: 'linear-gradient(135deg, #f56c6c, #e04545)', ...buildSpark(trend.value.complaints) }
])

const modules = [
  { title: '订单管理', desc: '下单 / 接单 / 配送全流程', icon: 'Document', bg: '#eef4ff', color: '#409eff' },
  { title: '评价管理', desc: '用户评价与客服回复', icon: 'Star', bg: '#fff7e6', color: '#f6a821' },
  { title: '申诉投诉', desc: '处理用户申诉与投诉', icon: 'ChatDotRound', bg: '#fdeeee', color: '#f56c6c' },
  { title: '信誉分管理', desc: '用户信誉分记录与申请', icon: 'Medal', bg: '#e6fbfb', color: '#13a8a8' },
  { title: '公告管理', desc: '发布系统公告', icon: 'Bell', bg: '#f3eefc', color: '#9065e0' },
  { title: '轮播图管理', desc: '首页轮播图配置', icon: 'PictureFilled', bg: '#eefbf0', color: '#52c41a' }
]

onMounted(() => {
  getStats().then((res) => (stats.value = res.data || {}))
  getTrend().then((res) => (trend.value = res.data || {}))
})
</script>

<style scoped>
.dashboard {
  padding: 4px;
}

.welcome-banner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 28px 32px;
  border-radius: 14px;
  color: #fff;
  background: linear-gradient(135deg, #409eff 0%, #2a5298 100%);
  box-shadow: 0 8px 22px rgba(64, 158, 255, 0.28);
  margin-bottom: 16px;
}

.welcome-title {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.welcome-sub {
  margin-top: 8px;
  font-size: 13px;
  opacity: 0.92;
}

.welcome-icon {
  color: rgba(255, 255, 255, 0.9);
  filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.15));
}

.stat-row {
  margin-bottom: 16px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 22px 20px;
  border-radius: 12px;
  color: #fff;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s, box-shadow 0.2s;
}

.stat-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.14);
}

.stat-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 56px;
  height: 56px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.22);
  flex-shrink: 0;
}

.stat-body {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  line-height: 1.2;
}

.stat-label {
  margin-top: 4px;
  font-size: 13px;
  opacity: 0.92;
}

.spark {
  width: 84px;
  height: 32px;
  flex-shrink: 0;
  opacity: 0.95;
}

.module-header {
  display: flex;
  align-items: baseline;
  gap: 10px;
}

.module-title {
  font-size: 15px;
  font-weight: 600;
  color: #1f2d3d;
}

.module-sub {
  font-size: 12px;
  color: #a0a6ad;
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 14px;
}

.module-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 16px;
  border: 1px solid #f0f2f5;
  border-radius: 12px;
  transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
}

.module-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 18px rgba(0, 0, 0, 0.06);
  border-color: #e4e7ed;
}

.module-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 44px;
  height: 44px;
  border-radius: 10px;
  flex-shrink: 0;
}

.module-name {
  font-size: 14px;
  font-weight: 600;
  color: #1f2d3d;
}

.module-desc {
  margin-top: 3px;
  font-size: 12px;
  color: #a0a6ad;
}
</style>
