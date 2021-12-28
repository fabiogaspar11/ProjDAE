import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)



export const state = () => ({
  username: null
})

export const mutations = {
  toggleUsername(state, value){
    state.username = value
  },
}
