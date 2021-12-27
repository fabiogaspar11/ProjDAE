import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _a42c148a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _4d6e28d9 = () => interopDefault(import('..\\pages\\biomedicData\\index.vue' /* webpackChunkName: "pages/biomedicData/index" */))
const _2712e44e = () => interopDefault(import('..\\pages\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/biomedicMeasures/index" */))
const _518fdb63 = () => interopDefault(import('..\\pages\\Dashboard.vue' /* webpackChunkName: "pages/Dashboard" */))
const _d3d0f9ac = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _0793d0f4 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _3787119a = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _460d58fa = () => interopDefault(import('..\\pages\\prescriptions\\index.vue' /* webpackChunkName: "pages/prescriptions/index" */))
const _1d0fdc60 = () => interopDefault(import('..\\pages\\auth\\Login.vue' /* webpackChunkName: "pages/auth/Login" */))
const _5ed7e5c4 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _4f38d9d3 = () => interopDefault(import('..\\pages\\biomedicData\\_code.vue' /* webpackChunkName: "pages/biomedicData/_code" */))
const _28dd9548 = () => interopDefault(import('..\\pages\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/biomedicMeasures/_code" */))
const _d03b97b8 = () => interopDefault(import('..\\pages\\diseases\\_code.vue' /* webpackChunkName: "pages/diseases/_code" */))
const _3efba3d7 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username" */))
const _3e1d65a8 = () => interopDefault(import('..\\pages\\patients\\_username\\index.vue' /* webpackChunkName: "pages/patients/_username/index" */))
const _47d809f4 = () => interopDefault(import('..\\pages\\prescriptions\\_code.vue' /* webpackChunkName: "pages/prescriptions/_code" */))
const _11dc7de9 = () => interopDefault(import('..\\pages\\patients\\_username\\biomedicMeasures.vue' /* webpackChunkName: "pages/patients/_username/biomedicMeasures" */))
const _3d02b8e6 = () => interopDefault(import('..\\pages\\patients\\_username\\diseases.vue' /* webpackChunkName: "pages/patients/_username/diseases" */))
const _682ecbce = () => interopDefault(import('..\\pages\\patients\\_username\\healthcareProfessionals.vue' /* webpackChunkName: "pages/patients/_username/healthcareProfessionals" */))
const _02b66673 = () => interopDefault(import('..\\pages\\patients\\_username\\prescriptions.vue' /* webpackChunkName: "pages/patients/_username/prescriptions" */))
const _373b5e41 = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages/index" */))

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
    component: _3e1d65a8,
    name: "patients-username"
  }, {
    path: "/prescriptions/:code",
    component: _5a05bfc8,
    name: "prescriptions-code"
  }, {
    path: "/patients/:username/biomedicMeasures",
    component: _11dc7de9,
    name: "patients-username-biomedicMeasures"
  }, {
    path: "/patients/:username/diseases",
    component: _3d02b8e6,
    name: "patients-username-diseases"
  }, {
    path: "/patients/:username/healthcareProfessionals",
    component: _682ecbce,
    name: "patients-username-healthcareProfessionals"
  }, {
    path: "/patients/:username/prescriptions",
    component: _02b66673,
    name: "patients-username-prescriptions"
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
