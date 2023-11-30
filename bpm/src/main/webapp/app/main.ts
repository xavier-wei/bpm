// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {library} from '@fortawesome/fontawesome-svg-core';
import {setupAxiosInterceptors} from '@/shared/config/axios-interceptor';
import App from './app.vue';
import BootstrapVue from 'bootstrap-vue';
import Vue2Filters from 'vue2-filters';
import {ToastPlugin} from 'bootstrap-vue';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import AlertService from './shared/alert/alert.service';
import VueCompositionAPI from '@vue/composition-api'
import Hooks, {useStore} from '@u3u/vue-hooks'
import {BootstrapVueIcons, ModalPlugin, VBTooltipPlugin} from 'bootstrap-vue';
import axios from 'axios';
import MenuService from './core/menu/menu-service';
import {trim as _trim, size as _size, keys as _keys, cloneDeep as _cloneDeep, clone as _clone} from 'lodash';

import '../content/scss/global.scss';
import '../content/scss/vendor.scss';
// import BpmTitleOptionsService from "@/shared/config/service/bpm-unit-options.service";
import BpmDeptsOptionsService from "@/shared/config/service/bpm-depts-options.service";
import BpmTitleOptionsService from "@/shared/config/service/bpm-title-options.service";

import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import BpmUserDataService from "@/shared/config/service/bpm-user-data-service";
/* tslint:disable */

// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
library.add(fas);
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(BootstrapVue);
Vue.use(Hooks);
Vue.use(Vue2Filters);
Vue.use(ToastPlugin);
Vue.use(VueCompositionAPI)
Vue.use(BootstrapVueIcons);
Vue.use(ModalPlugin);
Vue.use(VBTooltipPlugin);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);
const store = config.initVueXStore(Vue);
axios.defaults.baseURL = '/bpm/api';
new BpmDeptsOptionsService(store);
new BpmUserDataService(store);
new BpmTitleOptionsService(store);
const serviceUrlList: string[] = ['/login', '/service/', '/home'];
//透過hashMap處理上下一頁的props
const paramMap = {};

router.beforeEach(async (to, from, next) => {

    // no authorities, so just proceed
    const routeData = {
      fromPath: from.path,
      toPath: to.path,
      fromName: from.name,
      toName: to.name,
      isNotKeepAlive: Boolean(to.params.isNotKeepAlive),
    };
    store.commit('setRouteData', routeData);
    routeGuard(to, from, next);
    to.meta.title = '工程會EIP_表單流程管理';
    document.title = to.meta.title;
    next();


});

function routeGuard(to, from, next) {
  //紀錄router name對應的props，處理上下一頁props不見的問題
  recordProps(to);
  // console.log('接到paramMap',paramMap);
  //處理找不到router
  if (!to.matched.length) {
    next('/not-found');
  }

  //處理keep alive,之前是寫在if(authenticated)後才會加到keep alive,改成不管是否有auth都會keep alive
  if (to.params.isNotKeepAlive) {
    store.commit('removeKeepAlivePage', from.name);
  } else if (from.name) {
    store.commit('addKeepAlivePage', from.name);
  }


}

function recordProps(to) {
  if (to !== null && to !== undefined && to.name !== null && to.name !== undefined) {
    if (to.params !== null && to.params !== undefined && _size(_keys(to.params)) !== 0) {
      paramMap[to.name] = to.params;
      //如果to的param是空的,就去map裡面找,並塞給to
    } else {
      const inMemoryParam = _cloneDeep(paramMap[to.name]);
      if (inMemoryParam !== null && inMemoryParam !== undefined) Object.assign(to.params, inMemoryParam);
    }
  }
}




/* tslint:disable */
const vue = new Vue({
  el: '#app',
  components: {App},
  template: '<App/>',
  router,
  provide: {

    menuService: () => new MenuService,
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    alertService: () => new AlertService(),
  },
  store,
});

setupAxiosInterceptors(
  error => {
    const url = error.response?.config?.url;
    const status = error.status || error.response.status;
    if (status === 401) {
      // Store logged out state.
      store.commit('logout');
      if (!url.endsWith('api/account') && !url.endsWith('api/authenticate')) {
        // Ask for a new authentication
        return;
      }
    }
    console.log('Unauthorized!');
    return Promise.reject(error);
  },
  error => {
    console.log('Server error!');
    return Promise.reject(error);
  }
);
