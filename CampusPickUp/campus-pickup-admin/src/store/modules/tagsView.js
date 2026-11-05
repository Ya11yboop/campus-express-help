import { defineStore } from 'pinia'

export const useTagsViewStore = defineStore('tagsView', {
  state: () => ({
    visitedViews: [{ path: '/index', title: '首页', affix: true }]
  }),
  actions: {
    addView(view) {
      if (!view || !view.path) return
      if (this.visitedViews.some((v) => v.path === view.path)) return
      this.visitedViews.push({ ...view })
    },
    delView(path) {
      this.visitedViews = this.visitedViews.filter((v) => v.path !== path)
    },
    delOthers(path) {
      this.visitedViews = this.visitedViews.filter((v) => v.path === path || v.affix)
    },
    delAll() {
      this.visitedViews = this.visitedViews.filter((v) => v.affix)
    }
  }
})
