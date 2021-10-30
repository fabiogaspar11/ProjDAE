import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _5753310f = () => interopDefault(import('..\\pages\\administrators.vue' /* webpackChunkName: "pages/administrators" */))
const _36900db1 = () => interopDefault(import('..\\pages\\biomedicData.vue' /* webpackChunkName: "pages/biomedicData" */))
const _3d20df00 = () => interopDefault(import('..\\pages\\diseases.vue' /* webpackChunkName: "pages/diseases" */))
const _f8106e74 = () => interopDefault(import('..\\pages\\healthcareProfessionals.vue' /* webpackChunkName: "pages/healthcareProfessionals" */))
const _e6d1b6d2 = () => interopDefault(import('..\\pages\\patients.vue' /* webpackChunkName: "pages/patients" */))
const _61bacd40 = () => interopDefault(import('..\\pages\\prescriptions.vue' /* webpackChunkName: "pages/prescriptions" */))
const _a43c85ce = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages/index" */))

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
    component: _5753310f,
    name: "administrators"
  }, {
    path: "/biomedicData",
    component: _36900db1,
    name: "biomedicData"
  }, {
    path: "/diseases",
    component: _3d20df00,
    name: "diseases"
  }, {
    path: "/healthcareProfessionals",
    component: _f8106e74,
    name: "healthcareProfessionals"
  }, {
    path: "/patients",
    component: _e6d1b6d2,
    name: "patients"
  }, {
    path: "/prescriptions",
    component: _61bacd40,
    name: "prescriptions"
  }, {
    path: "/",
    component: _a43c85ce,
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
