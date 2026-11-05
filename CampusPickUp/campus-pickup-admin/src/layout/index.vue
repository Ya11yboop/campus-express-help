<template>
  <div class="app-wrapper">
    <Sidebar class="sidebar-container" />
    <div class="main-container">
      <Navbar />
      <TagsView />
      <AppMain />
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted } from 'vue'
import { ElNotification } from 'element-plus'
import Sidebar from './components/Sidebar/index.vue'
import Navbar from './components/Navbar.vue'
import TagsView from './components/TagsView.vue'
import AppMain from './components/AppMain.vue'

let ws = null

function connectWs() {
  ws = new WebSocket('ws://localhost:8080/ws/notify')
  ws.onmessage = (e) => {
    try {
      const data = JSON.parse(e.data)
      if (data.type === 'finishApply') {
        ElNotification({ title: '配送完成申请', message: data.msg, type: 'warning', duration: 0 })
      }
    } catch (err) {}
  }
  ws.onclose = () => { setTimeout(connectWs, 5000) }
}

onMounted(connectWs)
onUnmounted(() => { if (ws) ws.close() })
</script>

<style scoped>
.app-wrapper {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.sidebar-container {
  width: 210px;
  height: 100%;
  flex-shrink: 0;
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
