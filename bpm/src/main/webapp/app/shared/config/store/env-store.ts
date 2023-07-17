import { Module } from 'vuex';

export interface envState {
  PCCModeKey: string;
  PCCTestModeKey: string;
  prodModeKey: string;
  devModeKey: string;
  PCCMode: boolean;
  PCCTestMode: boolean;
  prodMode: boolean;
  devMode: boolean;
  mobileDevice: boolean;
  padDevice: boolean;
  deskTopDevice: boolean;
  currentWidth: number;
  currentHeight: number;
  mobileUpperLimit: number;
  padLowerLimit: number;
  padUpperLimit: number;
  deskTopLowerLimit: number;
  errorCollector: any;
}
export const envStore: Module<envState, any> = {
  state: {
    PCCModeKey: 'dr',
    PCCTestModeKey: 'pcc',
    prodModeKey: 'prod',
    devModeKey: 'dev',
    PCCMode: false,
    PCCTestMode: false,
    prodMode: false,
    devMode: false,
    mobileDevice: false,
    padDevice: false,
    deskTopDevice: false,
    currentWidth: 0,
    currentHeight: 0,
    mobileUpperLimit: 768,
    padLowerLimit: 768,
    padUpperLimit: 1024,
    deskTopLowerLimit: 1024,
    errorCollector: [],
  },
  getters: {
    PCCModeKey: state => state.PCCModeKey,
    PCCTestModeKey: state => state.PCCTestModeKey,
    prodModeKey: state => state.prodModeKey,
    devModeKey: state => state.devModeKey,
    PCCMode: state => state.PCCMode,
    PCCTestMode: state => state.PCCTestMode,
    prodMode: state => state.prodMode,
    devMode: state => state.devMode,
    mobileDevice: state => state.mobileDevice,
    padDevice: state => state.padDevice,
    deskTopDevice: state => state.deskTopDevice,
    currentWidth: state => state.currentWidth,
    currentHeight: state => state.currentHeight,
    mobileUpperLimit: state => state.mobileUpperLimit,
    padLowerLimit: state => state.padLowerLimit,
    padUpperLimit: state => state.padUpperLimit,
    deskTopLowerLimit: state => state.deskTopLowerLimit,
    errorCollector: state => state.errorCollector,
  },
  mutations: {
    initEnvProperties(state, mode) {
      state.PCCMode = mode === state.PCCModeKey;
      state.PCCTestMode = mode === state.PCCTestModeKey;
      state.prodMode = mode === state.prodModeKey;
      state.devMode = mode === state.devModeKey;
      state.mobileDevice = window.innerWidth < state.mobileUpperLimit;
      state.padDevice = window.innerWidth >= state.padLowerLimit && window.innerWidth < state.padUpperLimit;
      state.deskTopDevice = window.innerWidth >= state.deskTopLowerLimit;
    },
    setMobileDevice(state, isMobileDevice) {
      state.mobileDevice = isMobileDevice;
    },
    setPadDevice(state, isPadDevice) {
      state.padDevice = isPadDevice;
    },
    setDeskTopDevice(state, isDeskTopDevice) {
      state.deskTopDevice = isDeskTopDevice;
    },
    setCurrentWidth(state, currentWidth) {
      state.currentWidth = currentWidth;
    },
    setCurrentHeight(state, currentHeight) {
      state.currentHeight = currentHeight;
    },
    setErrorCollector(state, arr) {
      state.errorCollector = arr;
    },
  },
};
