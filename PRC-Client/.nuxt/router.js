import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _a42c148a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _76b1d862 = () => interopDefault(import('..\\pages\\biomedicDataTypes\\index.vue' /* webpackChunkName: "pages/biomedicDataTypes/index" */))
const _518fdb63 = () => interopDefault(import('..\\pages\\Dashboard.vue' /* webpackChunkName: "pages/Dashboard" */))
const _33ba5c89 = () => interopDefault(import('..\\pages\\DashboardAdministrators.vue' /* webpackChunkName: "pages/DashboardAdministrators" */))
const _6c70e68c = () => interopDefault(import('..\\pages\\DashboardHealthcareProfessionals.vue' /* webpackChunkName: "pages/DashboardHealthcareProfessionals" */))
const _d3d0f9ac = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _0793d0f4 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _3787119a = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _1d0fdc60 = () => interopDefault(import('..\\pages\\auth\\Login.vue' /* webpackChunkName: "pages/auth/Login" */))
const _5ed7e5c4 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _787c895c = () => interopDefault(import('..\\pages\\biomedicDataTypes\\_code.vue' /* webpackChunkName: "pages/biomedicDataTypes/_code" */))
const _beb3fe6a = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/index" */))
const _3e1d65a8 = () => interopDefault(import('..\\pages\\patients\\_username\\index.vue' /* webpackChunkName: "pages/patients/_username/index" */))
const _79b1b858 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/biomedicMeasures/index" */))
const _0d8cf53d = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\patients\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/patients/index" */))
const _fb27dda0 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\prescriptions\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/prescriptions/index" */))
const _11dc7de9 = () => interopDefault(import('..\\pages\\patients\\_username\\biomedicMeasures.vue' /* webpackChunkName: "pages/patients/_username/biomedicMeasures" */))
const _3d02b8e6 = () => interopDefault(import('..\\pages\\patients\\_username\\diseases.vue' /* webpackChunkName: "pages/patients/_username/diseases" */))
const _682ecbce = () => interopDefault(import('..\\pages\\patients\\_username\\healthcareProfessionals.vue' /* webpackChunkName: "pages/patients/_username/healthcareProfessionals" */))
const _02b66673 = () => interopDefault(import('..\\pages\\patients\\_username\\prescriptions.vue' /* webpackChunkName: "pages/patients/_username/prescriptions" */))
const _7b7c6952 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/biomedicMeasures/_code" */))
const _2f7c8e16 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\patients\\_usernamePatient.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/patients/_usernamePatient" */))
const _f7927bac = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\prescriptions\\_code.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/prescriptions/_code" */))
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
    path: "/biomedicDataTypes",
    component: _76b1d862,
    name: "biomedicDataTypes"
  }, {
    path: "/Dashboard",
    component: _518fdb63,
    name: "Dashboard"
  }, {
    path: "/DashboardAdministrators",
    component: _33ba5c89,
    name: "DashboardAdministrators"
  }, {
    path: "/DashboardHealthcareProfessionals",
    component: _6c70e68c,
    name: "DashboardHealthcareProfessionals"
  }, {
    path: "/diseases",
    component: _d3d0f9ac,
    name: "diseases"
  }, {
    path: "/healthCareProfessionals",
    component: _0793d0f4,
    name: "healthCareProfessionals"
  }, {
    path: "/patients",
    component: _3787119a,
    name: "patients"
  }, {
    path: "/auth/Login",
    component: _1d0fdc60,
    name: "auth-Login"
  }, {
    path: "/administrators/:username",
    component: _5ed7e5c4,
    name: "administrators-username"
  }, {
    path: "/biomedicDataTypes/:code",
    component: _787c895c,
    name: "biomedicDataTypes-code"
  }, {
    path: "/healthCareProfessionals/:username",
    component: _beb3fe6a,
    name: "healthCareProfessionals-username"
  }, {
    path: "/patients/:username",
    component: _3e1d65a8,
    name: "patients-username"
  }, {
    path: "/healthCareProfessionals/:username/biomedicMeasures",
    component: _79b1b858,
    name: "healthCareProfessionals-username-biomedicMeasures"
  }, {
    path: "/healthCareProfessionals/:username/patients",
    component: _0d8cf53d,
    name: "healthCareProfessionals-username-patients"
  }, {
    path: "/healthCareProfessionals/:username/prescriptions",
    component: _fb27dda0,
    name: "healthCareProfessionals-username-prescriptions"
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
    path: "/healthCareProfessionals/:username/biomedicMeasures/:code",
    component: _7b7c6952,
    name: "healthCareProfessionals-username-biomedicMeasures-code"
  }, {
    path: "/healthCareProfessionals/:username/patients/:usernamePatient",
    component: _2f7c8e16,
    name: "healthCareProfessionals-username-patients-usernamePatient"
  }, {
    path: "/healthCareProfessionals/:username/prescriptions/:code",
    component: _f7927bac,
    name: "healthCareProfessionals-username-prescriptions-code"
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
