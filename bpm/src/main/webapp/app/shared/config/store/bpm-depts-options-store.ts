import {Module} from "vuex";
import {DeptsModel} from "@/shared/model/bpm/deptsModel";

interface IDeptsData {
  eipDeptsData?: DeptsModel[] | null;
  eipDeptsDataPromise?: Promise<DeptsModel[]> | null;
  bpmDeptsOptions:[] ;
}

export const bpmDeptsOptionsStore: Module<IDeptsData, any> = {
  state: {
    eipDeptsData: [],
    eipDeptsDataPromise: Promise.resolve([]),
    bpmDeptsOptions:[],
  },
  actions: {
    eipDeptsData({ commit }, data) {
      commit('SET_DEPTS_DATA', data);
    },
    eipDeptsDataPromise({ commit }, data) {
      commit('SET_DEPTS_DATA_PROMISE', data);
    },
  },
  getters: {
    eipDeptsData: state => state.eipDeptsData,
    eipDeptsDataPromise: state => state.eipDeptsDataPromise,
    getBpmDeptsOptions: state => state.bpmDeptsOptions,
  },
  mutations: {
    SET_DEPTS_DATA(state, data) {
      state.eipDeptsData = data;
    },
    SET_DEPTS_DATA_PROMISE(state, data) {
      state.eipDeptsDataPromise = data;
    },
    setBpmDeptsOptions(state, data) {
      state.bpmDeptsOptions = data;
    },
  },

};
