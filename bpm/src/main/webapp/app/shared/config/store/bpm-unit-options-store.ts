import {Module} from "vuex";
import {EipCodeDataModel} from "@/shared/model/bpm/eipCodeDataModel";


interface IEipCodeData {
    eipCodeData?: EipCodeDataModel[] | null;
    eipCodeDataPromise?: Promise<EipCodeDataModel[]> | null;
    bpmUnitOptions:[] ;
}

export const bpmUnitOptionsStore: Module<IEipCodeData, any> = {
    state: {
        eipCodeData: [],
        eipCodeDataPromise: Promise.resolve([]),
        bpmUnitOptions:[],
    },
    actions: {
        eipCodeData({ commit }, data) {
            commit('SET_EIP_CODE_DATA', data);
        },
        eipCodeDataPromise({ commit }, data) {
            commit('SET_EIP_CODE_DATA_PROMISE', data);
        },
    },
    getters: {
        eipCodeData: state => state.eipCodeData,
        eipCodeDataPromise: state => state.eipCodeDataPromise,
        getBpmUnitOptions: state => state.bpmUnitOptions,
    },
    mutations: {
        SET_EIP_CODE_DATA(state, data) {
            state.eipCodeData = data;
        },
        SET_EIP_CODE_DATA_PROMISE(state, data) {
            state.eipCodeDataPromise = data;
        },
        setBpmUnitOptions(state, data) {
            state.bpmUnitOptions = data;
        },
    },

};
