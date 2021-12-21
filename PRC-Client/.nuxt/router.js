import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _a42c148a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _4d6e28d9 = () => interopDefault(import('..\\pages\\biomedicData\\index.vue' /* webpackChunkName: "pages/biomedicData/index" */))
const _2712e44e = () => interopDefault(import('..\\pages\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/biomedicMeasures/index" */))
const _d3d0f9ac = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _0793d0f4 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _3787119a = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _5ed7e5c4 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _4f38d9d3 = () => interopDefault(import('..\\pages\\biomedicData\\_code.vue' /* webpackChunkName: "pages/biomedicData/_code" */))
const _28dd9548 = () => interopDefault(import('..\\pages\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/biomedicMeasures/_code" */))
const _d03b97b8 = () => interopDefault(import('..\\pages\\diseases\\_code.vue' /* webpackChunkName: "pages/diseases/_code" */))
const _3efba3d7 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username" */))
const _a34ddad4 = () => interopDefault(import('..\\pages\\patients\\_username.vue' /* webpackChunkName: "pages/patients/_username" */))
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
    component: _2f1c4b8a,
    name: "administrators"
  }, {
    path: "/biomedicData",
    component: _30ecb330,
    name: "biomedicData"
  }, {
    path: "/biomedicMeasures",
    component: _2712e44e,
    name: "biomedicMeasures"
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
    path: "/administrators/:username",
    component: _21f8e36d,
    name: "administrators-username"
  }, {
    path: "/biomedicData/:code",
    component: _2d57513c,
    name: "biomedicData-code"
  }, {
    path: "/biomedicMeasures/:code",
    component: _28dd9548,
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
