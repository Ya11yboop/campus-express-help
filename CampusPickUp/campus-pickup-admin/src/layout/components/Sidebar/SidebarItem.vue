<template>
  <template v-if="!item.hidden">
    <el-sub-menu v-if="!isSingleChild && item.children && item.children.length" :index="resolvePath(item.path)">
      <template #title>
        <el-icon v-if="item.meta && item.meta.icon"><component :is="item.meta.icon" /></el-icon>
        <span>{{ item.meta && item.meta.title }}</span>
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :item="child"
        :base-path="resolvePath(item.path)"
      />
    </el-sub-menu>
    <el-menu-item v-else :index="menuIndex">
      <el-icon v-if="menuIcon"><component :is="menuIcon" /></el-icon>
      <template #title>{{ menuTitle }}</template>
    </el-menu-item>
  </template>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  item: { type: Object, required: true },
  basePath: { type: String, default: '' }
})

// 只有一个子路由且父级无标题时（如首页），直接把子路由作为一级菜单项，不显示子菜单
const isSingleChild = computed(() => {
  const children = props.item.children || []
  return children.length === 1 && !(props.item.meta && props.item.meta.title)
})

const menuItem = computed(() =>
  isSingleChild.value ? props.item.children[0] : props.item
)

const menuIndex = computed(() => resolvePath(menuItem.value.path))
const menuTitle = computed(() => menuItem.value.meta && menuItem.value.meta.title)
const menuIcon = computed(() => menuItem.value.meta && menuItem.value.meta.icon)

function resolvePath(routePath) {
  if (routePath.startsWith('/')) {
    return routePath
  }
  const base = props.basePath.endsWith('/') ? props.basePath.slice(0, -1) : props.basePath
  return base + '/' + routePath
}
</script>
