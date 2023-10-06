import { Module } from 'vuex'
import {EipCodeDataModel} from "@/shared/model/bpm/eipCodeDataModel";
import {UserModel} from "@/shared/model/bpm/userModel";

export interface PeoStateStorable {
  userData: UserModel[] | null;
  userAllData: UserModel[] | null;
}

// export const defaultPeoStateStorable: PeoStateStorable = {
//   userData: {user:'',unit:''},
// }

export const userStore: Module<PeoStateStorable, any> = {
  state: {
    userData:[],
    userAllData:[],
  },
  getters: {
    getUserData: state => state.userData,
    getUserAllData: state => state.userAllData,
  },
  mutations: {
    setUserData(state, value) {
      state.userData = value
    },
    setUserAllData(state, value) {
      state.userAllData = value
    },
  }
}
