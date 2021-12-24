import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _2f1c4b8a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _30ecb330 = () => interopDefault(import('..\\pages\\biomedicData\\index.vue' /* webpackChunkName: "pages/biomedicData/index" */))
const _2507025d = () => interopDefault(import('..\\pages\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/biomedicMeasures/index" */))
const _63b12fb2 = () => interopDefault(import('..\\pages\\Dashboard.vue' /* webpackChunkName: "pages/Dashboard" */))
const _ac13bf8e = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _aed8d7f6 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _0fc9d77c = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _213ce56a = () => interopDefault(import('..\\pages\\prescriptions\\index.vue' /* webpackChunkName: "pages/prescriptions/index" */))
const _21f8e36d = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _2d57513c = () => interopDefault(import('..\\pages\\biomedicData\\_code.vue' /* webpackChunkName: "pages/biomedicData/_code" */))
const _26d1b357 = () => interopDefault(import('..\\pages\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/biomedicMeasures/_code" */))
const _a87e5d9a = () => interopDefault(import('..\\pages\\diseases\\_code.vue' /* webpackChunkName: "pages/diseases/_code" */))
const _05517668 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username" */))
const _48749025 = () => interopDefault(import('..\\pages\\patients\\_username.vue' /* webpackChunkName: "pages/patients/_username" */))
const _1da78376 = () => interopDefault(import('..\\pages\\prescriptions\\_code.vue' /* webpackChunkName: "pages/prescriptions/_code" */))
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
    path: "/biomedicData",
    component: _30ecb330,
    name: "biomedicData"
  }, {
    path: "/biomedicMeasures",
    component: _2507025d,
    name: "biomedicMeasures"
  }, {
    path: "/Dashboard",
    component: _63b12fb2,
    name: "Dashboard"
  }, {
    path: "/diseases",
    component: _ac13bf8e,
    name: "diseases"
  }, {
    path: "/healthCareProfessionals",
    component: _aed8d7f6,
    name: "healthCareProfessionals"
  }, {
    path: "/patients",
    component: _0fc9d77c,
    name: "patients"
  }, {
    path: "/prescriptions",
    component: _213ce56a,
    name: "prescriptions"
  }, {
    path: "/administrators/:username",
    component: _21f8e36d,
    name: "administrators-username"
  }, {
    path: "/biomedicData/:code",
    component: _2d57513c,
    name: "biomedicData-code"
  }, {
    path: "/biomedicMeasures/:code",
    component: _26d1b357,
    name: "biomedicMeasures-code"
  }, {
    path: "/diseases/:code",
    component: _a87e5d9a,
    name: "diseases-code"
  }, {
    path: "/healthCareProfessionals/:username",
    component: _05517668,
    name: "healthCareProfessionals-username"
  }, {
    path: "/patients/:username",
    component: _48749025,
    name: "patients-username"
  }, {
    path: "/prescriptions/:code",
    component: _1da78376,
    name: "prescriptions-code"
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
