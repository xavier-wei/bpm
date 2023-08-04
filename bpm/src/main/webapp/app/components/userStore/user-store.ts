import { Module } from 'vuex'

export interface PeoStateStorable {
  userData: {user:string},
}

export const defaultPeoStateStorable: PeoStateStorable = {
  userData: {user:''},
}

export const userStore: Module<PeoStateStorable, any> = {
  state: {
    ...defaultPeoStateStorable
  },
  getters: {
    getUserData: state => state.userData,
  },
  mutations: {
    setUserData(state, value) {
      state.userData = value
    },
  }
}
