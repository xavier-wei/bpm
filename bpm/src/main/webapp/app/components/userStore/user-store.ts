import { Module } from 'vuex'

export interface PeoStateStorable {
  userData: {user:string,unit:string},
}

export const defaultPeoStateStorable: PeoStateStorable = {
  userData: {user:'',unit:''},
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
