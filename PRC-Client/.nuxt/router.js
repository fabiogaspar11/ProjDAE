import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _f130b6da = () => interopDefault(import('..\\pages\\administrators\\index.vue' /* webpackChunkName: "pages/administrators/index" */))
const _1f8f9a8a = () => interopDefault(import('..\\pages\\biomedicDataTypes\\index.vue' /* webpackChunkName: "pages/biomedicDataTypes/index" */))
const _3dbb0e3b = () => interopDefault(import('..\\pages\\Dashboard.vue' /* webpackChunkName: "pages/Dashboard" */))
const _b6f0953e = () => interopDefault(import('..\\pages\\DashboardAdministrators.vue' /* webpackChunkName: "pages/DashboardAdministrators" */))
const _21c3fe98 = () => interopDefault(import('..\\pages\\DashboardHealthcareProfessionals.vue' /* webpackChunkName: "pages/DashboardHealthcareProfessionals" */))
const _0fd73402 = () => interopDefault(import('..\\pages\\diseases\\index.vue' /* webpackChunkName: "pages/diseases/index" */))
const _d8dd55c8 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/index" */))
const _5dfc280b = () => interopDefault(import('..\\pages\\patients\\index.vue' /* webpackChunkName: "pages/patients/index" */))
const _7fa1eea8 = () => interopDefault(import('..\\pages\\auth\\Login.vue' /* webpackChunkName: "pages/auth/Login" */))
const _436e8ff6 = () => interopDefault(import('..\\pages\\administrators\\_username.vue' /* webpackChunkName: "pages/administrators/_username" */))
const _215a4b84 = () => interopDefault(import('..\\pages\\biomedicDataTypes\\_code.vue' /* webpackChunkName: "pages/biomedicDataTypes/_code" */))
const _718fd3a3 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/index" */))
const _6d31b558 = () => interopDefault(import('..\\pages\\patients\\_username\\index.vue' /* webpackChunkName: "pages/patients/_username/index" */))
const _e364f5a0 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/biomedicMeasures/index" */))
const _6a61cbd6 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\patients\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/patients/index" */))
const _c5119950 = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\prescriptions\\index.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/prescriptions/index" */))
const _5a202ca1 = () => interopDefault(import('..\\pages\\patients\\_username\\biomedicMeasures\\index.vue' /* webpackChunkName: "pages/patients/_username/biomedicMeasures/index" */))
const _e9a80b36 = () => interopDefault(import('..\\pages\\patients\\_username\\diseases.vue' /* webpackChunkName: "pages/patients/_username/diseases" */))
const _4637b841 = () => interopDefault(import('..\\pages\\patients\\_username\\healthcareProfessionals.vue' /* webpackChunkName: "pages/patients/_username/healthcareProfessionals" */))
const _e2f4d2ca = () => interopDefault(import('..\\pages\\patients\\_username\\prescriptions.vue' /* webpackChunkName: "pages/patients/_username/prescriptions" */))
const _dfcf93ac = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/biomedicMeasures/_code" */))
const _26e49d1d = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\patients\\_usernamePatient.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/patients/_usernamePatient" */))
const _c17c375c = () => interopDefault(import('..\\pages\\healthCareProfessionals\\_username\\prescriptions\\_code.vue' /* webpackChunkName: "pages/healthCareProfessionals/_username/prescriptions/_code" */))
const _5beadd9b = () => interopDefault(import('..\\pages\\patients\\_username\\biomedicMeasures\\_code.vue' /* webpackChunkName: "pages/patients/_username/biomedicMeasures/_code" */))
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
    path: "/biomedicDataTypes",
    component: _1f8f9a8a,
    name: "biomedicDataTypes"
  }, {
    path: "/Dashboard",
    component: _3dbb0e3b,
    name: "Dashboard"
  }, {
    path: "/DashboardAdministrators",
    component: _b6f0953e,
    name: "DashboardAdministrators"
  }, {
    path: "/DashboardHealthcareProfessionals",
    component: _21c3fe98,
    name: "DashboardHealthcareProfessionals"
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
    path: "/auth/Login",
    component: _7fa1eea8,
    name: "auth-Login"
  }, {
    path: "/administrators/:username",
    component: _436e8ff6,
    name: "administrators-username"
  }, {
    path: "/biomedicDataTypes/:code",
    component: _215a4b84,
    name: "biomedicDataTypes-code"
  }, {
    path: "/healthCareProfessionals/:username",
    component: _718fd3a3,
    name: "healthCareProfessionals-username"
  }, {
    path: "/patients/:username",
    component: _6d31b558,
    name: "patients-username"
  }, {
    path: "/healthCareProfessionals/:username/biomedicMeasures",
    component: _e364f5a0,
    name: "healthCareProfessionals-username-biomedicMeasures"
  }, {
    path: "/healthCareProfessionals/:username/patients",
    component: _6a61cbd6,
    name: "healthCareProfessionals-username-patients"
  }, {
    path: "/healthCareProfessionals/:username/prescriptions",
    component: _c5119950,
    name: "healthCareProfessionals-username-prescriptions"
  }, {
    path: "/patients/:username/biomedicMeasures",
    component: _5a202ca1,
    name: "patients-username-biomedicMeasures"
  }, {
    path: "/patients/:username/diseases",
    component: _e9a80b36,
    name: "patients-username-diseases"
  }, {
    path: "/patients/:username/healthcareProfessionals",
    component: _4637b841,
    name: "patients-username-healthcareProfessionals"
  }, {
    path: "/patients/:username/prescriptions",
    component: _e2f4d2ca,
    name: "patients-username-prescriptions"
  }, {
    path: "/healthCareProfessionals/:username/biomedicMeasures/:code",
    component: _dfcf93ac,
    name: "healthCareProfessionals-username-biomedicMeasures-code"
  }, {
    path: "/healthCareProfessionals/:username/patients/:usernamePatient",
    component: _26e49d1d,
    name: "healthCareProfessionals-username-patients-usernamePatient"
  }, {
    path: "/healthCareProfessionals/:username/prescriptions/:code",
    component: _c17c375c,
    name: "healthCareProfessionals-username-prescriptions-code"
  }, {
    path: "/patients/:username/biomedicMeasures/:code",
    component: _5beadd9b,
    name: "patients-username-biomedicMeasures-code"
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
