import { Module } from 'vuex';

export const axiosScrollStore: Module<any, any> = {
  state: {
    isAxiosScroll: false,
  },
  getters: {
    isAxiosScroll: state => state.isAxiosScroll,
  },
  mutations: {
    setAxiosScroll(state, data) {
      state.isAxiosScroll = data;
    },
  },
};
