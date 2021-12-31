import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _2f1c4b8a = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _37417c33 = () => interopDefault(import('..\\pages\\biomedicDataTypes\\index.vue' /* webpackChunkName: "pages/biomedicDataTypes/index" */))
const _2507025d = () => interopDefault(import('..\\pages\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/biomedicMeasures/index" */))
const _63b12fb2 = () => interopDefault(import('..\\pages\\Dashboard.vue' /* webpackChunkName: "pages/Dashboard" */))
const _31ae7a98 = () => interopDefault(import('..\\pages\\DashboardAdministrators.vue' /* webpackChunkName: "pages/DashboardAdministrators" */))
const _66e7ad46 = () => interopDefault(import('..\\pages\\DashboardHealthcareProfessionals.vue' /* webpackChunkName: "pages/DashboardHealthcareProfessionals" */))
const _ac13bf8e = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _aed8d7f6 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _0fc9d77c = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _213ce56a = () => interopDefault(import('..\\pages\\prescriptions\\index.vue' /* webpackChunkName: "pages/prescriptions/index" */))
const _00958e5f = () => interopDefault(import('..\\pages\\auth\\Login.vue' /* webpackChunkName: "pages/auth/Login" */))
const _21f8e36d = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _390c2d2d = () => interopDefault(import('..\\pages\\biomedicDataTypes\\_code.vue' /* webpackChunkName: "pages/biomedicDataTypes/_code" */))
const _26d1b357 = () => interopDefault(import('..\\pages\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/biomedicMeasures/_code" */))
const _a87e5d9a = () => interopDefault(import('..\\pages\\diseases\\_code.vue' /* webpackChunkName: "pages/diseases/_code" */))
const _e3eb4dcc = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/index" */))
const _87b17e86 = () => interopDefault(import('..\\pages\\patients\\_username\\index.vue' /* webpackChunkName: "pages/patients/_username/index" */))
const _1da78376 = () => interopDefault(import('..\\pages\\prescriptions\\_code.vue' /* webpackChunkName: "pages/prescriptions/_code" */))
const _5f443ae7 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/biomedicMeasures/index" */))
const _1df676cc = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\patients\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/patients/index" */))
const _60d03101 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\prescriptions\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/prescriptions/index" */))
const _e1c15490 = () => interopDefault(import('..\\pages\\patients\\_username\\biomedicMeasures.vue' /* webpackChunkName: "pages/patients/_username/biomedicMeasures" */))
const _a63c8b48 = () => interopDefault(import('..\\pages\\patients\\_username\\diseases.vue' /* webpackChunkName: "pages/patients/_username/diseases" */))
const _5c292b6a = () => interopDefault(import('..\\pages\\patients\\_username\\healthcareProfessionals.vue' /* webpackChunkName: "pages/patients/_username/healthcareProfessionals" */))
const _6de78df8 = () => interopDefault(import('..\\pages\\patients\\_username\\prescriptions.vue' /* webpackChunkName: "pages/patients/_username/prescriptions" */))
const _610eebe1 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/biomedicMeasures/_code" */))
const _350cedf4 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\patients\\_usernemaPatient.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/patients/_usernemaPatient" */))
const _629ae1fb = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\prescriptions\\_code.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/prescriptions/_code" */))
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
    component: _a42c148a,
    name: "administrators"
  }, {
    path: "/biomedicDataTypes",
    component: _37417c33,
    name: "biomedicDataTypes"
  }, {
    path: "/biomedicMeasures",
    component: _2712e44e,
    name: "biomedicMeasures"
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
    path: "/prescriptions",
    component: _460d58fa,
    name: "prescriptions"
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
    component: _390c2d2d,
    name: "biomedicDataTypes-code"
  }, {
    path: "/biomedicMeasures/:code",
    component: _28dd9548,
    name: "biomedicMeasures-code"
  }, {
    path: "/diseases/:code",
    component: _d03b97b8,
    name: "diseases-code"
  }, {
    path: "/healthCareProfessionals/:username",
    component: _beb3fe6a,
    name: "healthCareProfessionals-username"
  }, {
    path: "/patients/:username",
    component: _3e1d65a8,
    name: "patients-username"
  }, {
    path: "/prescriptions/:code",
    component: _47d809f4,
    name: "prescriptions-code"
  }, {
    path: "/healthCareProfessionals/:username/biomedicMeasures",
    component: _5f443ae7,
    name: "healthCareProfessionals-username-biomedicMeasures"
  }, {
    path: "/healthCareProfessionals/:username/patients",
    component: _1df676cc,
    name: "healthCareProfessionals-username-patients"
  }, {
    path: "/healthCareProfessionals/:username/prescriptions",
    component: _60d03101,
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
    component: _610eebe1,
    name: "healthCareProfessionals-username-biomedicMeasures-code"
  }, {
    path: "/healthCareProfessionals/:username/patients/:usernemaPatient",
    component: _350cedf4,
    name: "healthCareProfessionals-username-patients-usernemaPatient"
  }, {
    path: "/healthCareProfessionals/:username/prescriptions/:code",
    component: _629ae1fb,
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
