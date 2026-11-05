<template>
  <div class="tags-view">
    <el-scrollbar class="tags-scrollbar">
      <div class="tags-scroll">
        <router-link
          v-for="tag in tags"
          :key="tag.path"
          :to="tag.path"
          class="tag-item"
          :class="{ active: isActive(tag) }"
        >
          <span class="dot" v-if="!tag.affix"></span>
          <span class="tag-title">{{ tag.title }}</span>
          <el-icon v-if="!tag.affix" class="tag-close" @click.prevent.stop="closeTag(tag)"><Close /></el-icon>
        </router-link>
      </div>
    </el-scrollbar>

    <el-dropdown class="tags-actions" @command="handleCommand">
      <span class="tags-actions-btn"><el-icon><ArrowDown /></el-icon></span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="others">关闭其他</el-dropdown-item>
          <el-dropdown-item command="all">关闭全部</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup>
import { computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTagsViewStore } from '@/store/modules/tagsView'

const route = useRoute()
const router = useRouter()
const store = useTagsViewStore()

const tags = computed(() => store.visitedViews)

function isActive(tag) {
  return tag.path === route.path
}

function addCurrent() {
  if (route.meta && route.meta.title) {
    store.addView({ path: route.path, title: route.meta.title })
  }
}

function closeTag(tag) {
  store.delView(tag.path)
  if (isActive(tag)) {
    const last = tags.value[tags.value.length - 1]
    router.push(last ? last.path : '/index')
  }
}

function handleCommand(cmd) {
  if (cmd === 'others') {
    store.delOthers(route.path)
  } else if (cmd === 'all') {
    store.delAll()
    router.push('/index')
  }
}

watch(() => route.path, addCurrent, { immediate: true })
</script>

<style scoped>
.tags-view {
  display: flex;
  align-items: center;
  height: 40px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  box-shadow: 0 1px 3px rgba(0, 21, 41, 0.05);
  padding: 0 10px;
}

.tags-scrollbar {
  flex: 1;
}

.tags-scroll {
  display: flex;
  align-items: center;
  gap: 6px;
  white-space: nowrap;
}

.tag-item {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  height: 26px;
  padding: 0 10px;
  border: 1px solid #d8dce5;
  border-radius: 4px;
  color: #495060;
  font-size: 12px;
  background: #fff;
  transition: all 0.2s;
}

.tag-item:hover {
  color: #409eff;
  border-color: #409eff;
}

.tag-item.active {
  color: #fff;
  background: #409eff;
  border-color: #409eff;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #fff;
  border: 2px solid #409eff;
}

.tag-item.active .dot {
  background: #fff;
  border-color: #fff;
}

.tag-close {
  border-radius: 50%;
  font-size: 12px;
  transition: background 0.2s;
}

.tag-close:hover {
  background: rgba(0, 0, 0, 0.1);
}

.tags-actions {
  flex-shrink: 0;
  margin-left: 8px;
}

.tags-actions-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 26px;
  height: 26px;
  border-radius: 4px;
  cursor: pointer;
  color: #666;
}

.tags-actions-btn:hover {
  background: #f0f2f5;
}
</style>
