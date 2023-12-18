import {Module} from "vuex";

interface IDeptsData {
  bpmDeptsOptions:[] ;
}

export const bpmDeptsOptionsStore: Module<IDeptsData, any> = {
  state: {
    bpmDeptsOptions:[],
  },
  getters: {
    getBpmDeptsOptions: state => state.bpmDeptsOptions,
  },
  mutations: {
    setBpmDeptsOptions(state, data) {
      state.bpmDeptsOptions = data;
    },
  },

};
