import Vue from 'vue'
import Vuex from 'vuex'
import JsonExcel from "vue-json-excel";

Vue.component("downloadExcel", JsonExcel);

Vue.use(Vuex)



export const state = () => ({
  username: null
})

export const mutations = {
  toggleUsername(state, value){
    state.username = value
  },
}
