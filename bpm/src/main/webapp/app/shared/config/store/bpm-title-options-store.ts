import {Module} from "vuex";
import {EipCodeDataModel} from "@/shared/model/bpm/eipCodeDataModel";


interface IEipCodeData {
  eipCodeData?: EipCodeDataModel[] | null;
  eipCodeDataPromise?: Promise<EipCodeDataModel[]> | null;
  bpmTitleOptions: [];
}

export const bpmTitleOptionsStore: Module<IEipCodeData, any> = {
  state: {
    eipCodeData: [],
    eipCodeDataPromise: Promise.resolve([]),
    bpmTitleOptions: [],
  },
  actions: {
    eipCodeData({commit}, data) {
      commit('SET_EIP_CODE_DATA', data);
    },
    eipCodeDataPromise({commit}, data) {
      commit('SET_EIP_CODE_DATA_PROMISE', data);
    },
  },
  getters: {
    eipCodeData: state => state.eipCodeData,
    eipCodeDataPromise: state => state.eipCodeDataPromise,
    getBpmTitleOptions: state => state.bpmTitleOptions,
  },
  mutations: {
    SET_EIP_CODE_DATA(state, data) {
      state.eipCodeData = data;
    },
    SET_EIP_CODE_DATA_PROMISE(state, data) {
      state.eipCodeDataPromise = data;
    },
    setBpmTitleOptions(state, data) {
      state.bpmTitleOptions = data;
    },
  },

};
