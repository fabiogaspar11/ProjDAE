import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _2f1c4b8a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _ac13bf8e = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _0fc9d77c = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _d7f2860c = () => interopDefault(import('..\\pages\\diseases\\teste.vue' /* webpackChunkName: "pages/diseases/teste" */))
const _21f8e36d = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _48749025 = () => interopDefault(import('..\\pages\\patients\\_username.vue' /* webpackChunkName: "pages/patients/_username" */))
const _51cd9be0 = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages/index" */))

const emptyFn = () => {}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: '/',
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
    path: "/administrators",
    component: _2f1c4b8a,
    name: "administrators"
  }, {
    path: "/diseases",
    component: _ac13bf8e,
    name: "diseases"
  }, {
    path: "/patients",
    component: _0fc9d77c,
    name: "patients"
  }, {
    path: "/diseases/teste",
    component: _d7f2860c,
    name: "diseases-teste"
  }, {
    path: "/administrators/:username",
    component: _21f8e36d,
    name: "administrators-username"
  }, {
    path: "/patients/:username",
    component: _48749025,
    name: "patients-username"
  }, {
    path: "/",
    component: _51cd9be0,
    name: "index"
  }],

  fallback: false
}

export function createRouter (ssrContext, config) {
  const base = (config._app && config._app.basePath) || routerOptions.base
  const router = new Router({ ...routerOptions, base  })

  // TODO: remove in Nuxt 3
  const originalPush = router.push
  router.push = function push (location, onComplete = emptyFn, onAbort) {
    return originalPush.call(this, location, onComplete, onAbort)
  }

  const resolve = router.resolve.bind(router)
  router.resolve = (to, current, append) => {
    if (typeof to === 'string') {
      to = normalizeURL(to)
    }
    return resolve(to, current, append)
  }

  return router
}
