<template>
  <div class="app">
<!--    <div class="sidebar">-->
<!--      &lt;!&ndash; 側邊攔内容 &ndash;&gt;-->
<!--      <Home @reloadContent="reload"></Home>-->
<!--    </div>-->

    <!--Loading畫面-->
    <block-ui :is-loading="isLoading"></block-ui>

    <!--麵包屑-->
<!--    <div class="d-flex">-->
<!--      <div class="bg pb-3 col-md-10">-->
<!--&lt;!&ndash;        <breadcrumb></breadcrumb>&ndash;&gt;-->
<!--   -->
<!--      </div>-->
<!--    </div>-->

    <keep-alive :include="keepAlivePage">
      <router-view v-if="isContentAlive"></router-view>
    </keep-alive>
  </div>
</template>

<script lang="ts">
import Ribbon from '../app/core/ribbon/ribbon.vue';
import JhiFooter from '../app/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '../app/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '../app/account/login-form/login-form.vue';
// import Home from '@/components/home.vue';
import {computed, nextTick, onMounted, provide, reactive, ref, watch, inject} from '@vue/composition-api';
import {BButton, BIcon, BSidebar, BvModal, BRow, BFormSelect, BFormSelectOption} from 'bootstrap-vue';
import '@/shared/config/dayjs';
import {useGetters, useRouter, useStore} from '@u3u/vue-hooks';
import MenuService from '@/core/menu/menu-service';
import NotificationService from './shared/notification/notification-service';
import axios from 'axios';
import Breadcrumb from '@/core/menu/breadcrumb.vue';
import BlockUi from '@/core/block-ui/block-ui.vue';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";

const ALERT_HEADER = 'x-pwc-alert';
const ALERT_MESSAGE = 'x-pwc-params';

export default {
  name: 'app',
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'jhi-footer': JhiFooter,
    Breadcrumb,
    BlockUi,
    // Home,
  },
  setup(prop, context) {
    provide<BvModal>('$bvModal', overrideBvModal(context.root.$bvModal));

    function overrideBvModal(bvModal: BvModal): BvModal {
      return {
        msgBoxOk: (message, options) =>
            bvModal.msgBoxOk(message, {
              ...options,
              okTitle: options && options.okTitle ? options.okTitle : '確定',
            }),
        msgBoxConfirm: (message, options) =>
            bvModal.msgBoxConfirm(message, {
              ...options,
              okTitle: options && options.okTitle ? options.okTitle : '確定',
              cancelTitle: options && options.cancelTitle ? options.cancelTitle : '取消',
            }),
        show: id => bvModal.show(id),
        hide: id => bvModal.hide(id),
      };
    }

    const mobileUpperLimit = computed(() => useGetters(['mobileUpperLimit']).mobileUpperLimit.value);
    const currentWidth = computed(() => useGetters(['currentWidth']).currentWidth.value);
    const padLowerLimit = computed(() => useGetters(['padLowerLimit']).padLowerLimit.value);
    const padUpperLimit = computed(() => useGetters(['padUpperLimit']).padUpperLimit.value);
    const deskTopLowerLimit = computed(() => useGetters(['deskTopLowerLimit']).deskTopLowerLimit.value);
    // useStore().value.commit('initEnvProperties', process.env.ENV_PROFILE);
    const routeData = computed(() => useGetters(['routeData']).routeData.value);

    const menuService = inject<() => MenuService>('menuService')();
    const notificationService = new NotificationService(context.root);
    const isContentAlive = ref(true);
    const dynamicSizeForDev = () => {
      window.addEventListener('resize', e => {
        useStore().value.commit('setCurrentWidth', window.innerWidth);
        useStore().value.commit('setCurrentHeight', window.innerHeight);
        // const isMobileDevice = currentWidth.value < mobileUpperLimit.value;
        const isPadDevice = currentWidth.value >= padLowerLimit.value && currentWidth.value < padUpperLimit.value;
        const isDeskTopDevice = currentWidth.value >= deskTopLowerLimit.value;
        // useStore().value.commit('setMobileDevice', isMobileDevice);
        useStore().value.commit('setPadDevice', isPadDevice);
        useStore().value.commit('setDeskTopDevice', isDeskTopDevice);
        // useStore().value.commit('setMenuState', isDeskTopDevice);
      });
    };

    axios.interceptors.response.use(
        response => {
          if (response.headers[ALERT_HEADER] === 'SUCCESS' || response.headers[ALERT_HEADER] === 'INFO') {
            notificationService.info(decodeURIComponent(response.headers[ALERT_MESSAGE]));
          }
          return response;
        },
        error => {
          return Promise.reject(error);
        }
    );

    const lockInputTypeNumberWheelEvent = () => {
      document.addEventListener('wheel', e => {
        if (document.activeElement.type !== 'number') return;
        document.activeElement.blur();
      });
    };

    const reload = () => {
      isContentAlive.value = false;
      nextTick(() => (isContentAlive.value = true));
    };

    provide('menuService', menuService);
    provide<NotificationService>('notificationService', notificationService);
    provide<BvModal>('$bvModal', overrideBvModal(context.root.$bvModal));

    onMounted(() => {
      dynamicSizeForDev();
      lockInputTypeNumberWheelEvent();

    });

    return {
      isContentAlive,
      ...useGetters(['routeData', 'isLoading', 'keepAlivePage']),
      reload
    }
  },
};
</script>
<style lang="scss">
.app {
  background-color: white;
}

.sidebar {
  height: 100%;
  width: 300px;
  background-color: white;
  float: left;
}

.main {
  padding: 10px;
  width: 100vw;
  height: 100%;
  background-color: white;
}


</style>
