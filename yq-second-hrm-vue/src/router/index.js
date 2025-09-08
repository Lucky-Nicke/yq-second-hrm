import Vue from 'vue'
import VueRouter from 'vue-router'
import iFrameView from '@/views/iFrameView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'iFrame',
    component: iFrameView
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
