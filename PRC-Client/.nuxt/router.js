import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _a42c148a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _4d6e28d9 = () => interopDefault(import('..\\pages\\biomedicData\\index.vue' /* webpackChunkName: "pages/biomedicData/index" */))
const _d3d0f9ac = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _1211ce04 = () => interopDefault(import('..\\pages\\diseaseTypes\\index.vue' /* webpackChunkName: "pages/diseaseTypes/index" */))
const _0793d0f4 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _3787119a = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _5ed7e5c4 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _4f38d9d3 = () => interopDefault(import('..\\pages\\biomedicData\\_code.vue' /* webpackChunkName: "pages/biomedicData/_code" */))
const _d03b97b8 = () => interopDefault(import('..\\pages\\diseases\\_code.vue' /* webpackChunkName: "pages/diseases/_code" */))
const _13dc7efe = () => interopDefault(import('..\\pages\\diseaseTypes\\_code.vue' /* webpackChunkName: "pages/diseaseTypes/_code" */))
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
    component: _a42c148a,
    name: "administrators"
  }, {
    path: "/biomedicData",
    component: _4d6e28d9,
    name: "biomedicData"
  }, {
    path: "/diseases",
    component: _d3d0f9ac,
    name: "diseases"
  }, {
    path: "/diseaseTypes",
    component: _1211ce04,
    name: "diseaseTypes"
  }, {
    path: "/healthCareProfessionals",
    component: _0793d0f4,
    name: "healthCareProfessionals"
  }, {
    path: "/patients",
    component: _3787119a,
    name: "patients"
  }, {
    path: "/administrators/:username",
    component: _5ed7e5c4,
    name: "administrators-username"
  }, {
    path: "/biomedicData/:code",
    component: _4f38d9d3,
    name: "biomedicData-code"
  }, {
    path: "/diseases/:code",
    component: _d03b97b8,
    name: "diseases-code"
  }, {
    path: "/diseaseTypes/:code",
    component: _13dc7efe,
    name: "diseaseTypes-code"
  }, {
    path: "/healthCareProfessionals/:username",
    component: _3efba3d7,
    name: "healthCareProfessionals-username"
  }, {
    path: "/patients/:username",
    component: _a34ddad4,
    name: "patients-username"
  }, {
    path: "/",
    component: _373b5e41,
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
