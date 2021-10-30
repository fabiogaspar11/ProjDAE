import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _5753310f = () => interopDefault(import('..\\pages\\administrators.vue' /* webpackChunkName: "pages/administrators" */))
const _f130b6da = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _436e8ff6 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _36900db1 = () => interopDefault(import('..\\pages\\biomedicData.vue' /* webpackChunkName: "pages/biomedicData" */))
const _3d20df00 = () => interopDefault(import('..\\pages\\diseases.vue' /* webpackChunkName: "pages/diseases" */))
const _f8106e74 = () => interopDefault(import('..\\pages\\healthcareProfessionals.vue' /* webpackChunkName: "pages/healthcareProfessionals" */))
const _5dfc280b = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _61bacd40 = () => interopDefault(import('..\\pages\\prescriptions.vue' /* webpackChunkName: "pages/prescriptions" */))
const _26b6d124 = () => interopDefault(import('..\\pages\\patients\\_username.vue' /* webpackChunkName: "pages/patients/_username" */))
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
    children: [{
      path: "",
      component: _f130b6da,
      name: "administrators"
    }, {
      path: ":username",
      component: _436e8ff6,
      name: "administrators-username"
    }]
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
    component: _5dfc280b,
    name: "patients"
  }, {
    path: "/prescriptions",
    component: _61bacd40,
    name: "prescriptions"
  }, {
    path: "/patients/:username",
    component: _26b6d124,
    name: "patients-username"
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
