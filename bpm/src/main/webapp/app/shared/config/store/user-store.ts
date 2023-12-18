import { Module } from 'vuex'
import {UserModel} from "@/shared/model/bpm/userModel";

export interface IUserStore {
  userData: UserModel[] | null;
  userAllData: UserModel[] | null;
}

export const userStore: Module<IUserStore, any> = {
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
