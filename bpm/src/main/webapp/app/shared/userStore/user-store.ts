import { Module } from 'vuex'
import {EipCodeDataModel} from "@/shared/model/bpm/eipCodeDataModel";
import {UserModel} from "@/shared/model/bpm/userModel";

export interface PeoStateStorable {
  userData: UserModel[] | null;
}

// export const defaultPeoStateStorable: PeoStateStorable = {
//   userData: {user:'',unit:''},
// }

export const userStore: Module<PeoStateStorable, any> = {
  state: {
    userData:[],
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
