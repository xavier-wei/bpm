import {Module} from "vuex";
import {EipCodeDataModel} from "@/shared/model/bpm/eipCodeDataModel";


interface IBpmTitleOptions {
  bpmTitleOptions: [];
  bpmTitleOptionsPromise?: Promise<EipCodeDataModel[]> | null;
}

export const bpmTitleOptionsStore: Module<IBpmTitleOptions, any> = {
  state: {
    bpmTitleOptions: [],
    bpmTitleOptionsPromise: Promise.resolve([]),
  },
  actions: {
    handleBpmTitleOptions({commit}, data) {
      commit('BPM_TITLE_OPTIONS', data);
    },
    handleBpmTitleOptionsPromise({commit}, data) {
      commit('BPM_TITLE_OPTIONS_PROMISE', data);
    },
  },
  mutations: {

    BPM_TITLE_OPTIONS(state, data) {
      state.bpmTitleOptions = data;
    },
    BPM_TITLE_OPTIONS_PROMISE(state, data) {
      state.bpmTitleOptionsPromise = data;
    },
  },
  getters: {
    getBpmTitleOptions: state => state.bpmTitleOptions,
    getBpmTitleOptionsPromise: state => state.bpmTitleOptionsPromise,
  },
};
