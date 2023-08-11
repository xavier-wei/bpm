import { Module } from 'vuex';
import { filter as _filter, size as _size, cloneDeep as _cloneDeep, concat as _concat, dropRight as _dropRight } from 'lodash';

export const keepAliveStore: Module<any, any> = {
  state: {
    keepAlivePage: [],
    isLoading: false,
    axiosQueue: [],
    routeData: null,
  },
  getters: {
    keepAlivePage: state => state.keepAlivePage,
    isLoading: state => state.isLoading,
    axiosQueue: state => state.axiosQueue,
    routeData: state => state.routeData,
  },
  mutations: {
    addKeepAlivePage(state, name) {
      const nonDistinctArray = state.keepAlivePage.concat(name);
      state.keepAlivePage = nonDistinctArray.filter((target, index, array) => array.indexOf(target) == index);
    },
    removeKeepAlivePage(state, name) {
      state.keepAlivePage = state.keepAlivePage.filter(page => page.toLowerCase() !== name.toLowerCase());
    },
    clearKeepAlivePages(state) {
      state.keepAlivePage.splice(0, state.keepAlivePage.length);
    },
    setLoadingStatus(state, condition) {
      if (condition.status) state.axiosQueue.push(condition.url)
      else {
        const existArr = _filter(state.axiosQueue, axiosUrl => { return axiosUrl === condition.url; }),
          filterArr = _filter(state.axiosQueue, axiosUrl => { return axiosUrl !== condition.url; });
        if (_size(existArr) === 1) state.axiosQueue = _cloneDeep(filterArr);
        else if (_size(existArr) > 1) state.axiosQueue = _concat(filterArr, _dropRight(existArr));
      }
      state.isLoading = _size(state.axiosQueue) !== 0;
    },
    setRouteData(state, routeData) {
      if (!routeData.fromName) {
        routeData.fromName = 'home';
      }
      state.routeData = Object.assign({}, routeData);
    },
  },
};
