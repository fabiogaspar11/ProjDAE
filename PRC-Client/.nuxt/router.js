import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _f130b6da = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _e88ca49e = () => interopDefault(import('..\\pages\\biomedicData\\index.vue' /* webpackChunkName: "pages/biomedicData/index" */))
const _d03f85b4 = () => interopDefault(import('..\\pages\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/biomedicMeasures/index" */))
const _3dbb0e3b = () => interopDefault(import('..\\pages\\Dashboard.vue' /* webpackChunkName: "pages/Dashboard" */))
const _0fd73402 = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _d8dd55c8 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _5dfc280b = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _5d9b21bc = () => interopDefault(import('..\\pages\\prescriptions\\index.vue' /* webpackChunkName: "pages/prescriptions/index" */))
const _7fa1eea8 = () => interopDefault(import('..\\pages\\auth\\Login.vue' /* webpackChunkName: "pages/auth/Login" */))
const _436e8ff6 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _e4f742aa = () => interopDefault(import('..\\pages\\biomedicData\\_code.vue' /* webpackChunkName: "pages/biomedicData/_code" */))
const _ccaa23c0 = () => interopDefault(import('..\\pages\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/biomedicMeasures/_code" */))
const _11a1e4fc = () => interopDefault(import('..\\pages\\diseases\\_code.vue' /* webpackChunkName: "pages/diseases/_code" */))
const _6a6a5802 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username" */))
const _26b6d124 = () => interopDefault(import('..\\pages\\patients\\_username.vue' /* webpackChunkName: "pages/patients/_username" */))
const _5a05bfc8 = () => interopDefault(import('..\\pages\\prescriptions\\_code.vue' /* webpackChunkName: "pages/prescriptions/_code" */))
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
    component: _f130b6da,
    name: "administrators"
  }, {
    path: "/biomedicData",
    component: _e88ca49e,
    name: "biomedicData"
  }, {
    path: "/biomedicMeasures",
    component: _d03f85b4,
    name: "biomedicMeasures"
  }, {
    path: "/Dashboard",
    component: _3dbb0e3b,
    name: "Dashboard"
  }, {
    path: "/diseases",
    component: _0fd73402,
    name: "diseases"
  }, {
    path: "/healthCareProfessionals",
    component: _d8dd55c8,
    name: "healthCareProfessionals"
  }, {
    path: "/patients",
    component: _5dfc280b,
    name: "patients"
  }, {
    path: "/prescriptions",
    component: _5d9b21bc,
    name: "prescriptions"
  }, {
    path: "/auth/Login",
    component: _7fa1eea8,
    name: "auth-Login"
  }, {
    path: "/administrators/:username",
    component: _436e8ff6,
    name: "administrators-username"
  }, {
    path: "/biomedicData/:code",
    component: _e4f742aa,
    name: "biomedicData-code"
  }, {
    path: "/biomedicMeasures/:code",
    component: _ccaa23c0,
    name: "biomedicMeasures-code"
  }, {
    path: "/diseases/:code",
    component: _11a1e4fc,
    name: "diseases-code"
  }, {
    path: "/healthCareProfessionals/:username",
    component: _6a6a5802,
    name: "healthCareProfessionals-username"
  }, {
    path: "/patients/:username",
    component: _26b6d124,
    name: "patients-username"
  }, {
    path: "/prescriptions/:code",
    component: _5a05bfc8,
    name: "prescriptions-code"
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
