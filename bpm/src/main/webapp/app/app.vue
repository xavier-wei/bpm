<template>
  <div class="app">
    <!--    <div class="sidebar">-->
    <!--      &lt;!&ndash; 側邊攔内容 &ndash;&gt;-->
    <!--      <Home @reloadContent="reload"></Home>-->
    <!--    </div>-->

    <!--&lt;!&ndash;    麵包屑&ndash;&gt;-->
    <!--    <div class="d-flex">-->
    <!--      <div class="bg pb-3 col-md-10">-->
    <!--        <breadcrumb></breadcrumb>-->
    <!--   -->
    <!--      </div>-->
    <!--    </div>-->

    <!--Loading畫面-->
    <block-ui :is-loading="isLoading"></block-ui>

    <keep-alive :include="keepAlivePage">
      <router-view v-if="isContentAlive"></router-view>
    </keep-alive>
  </div>
</template>

<script lang="ts">
import Ribbon from '../app/core/ribbon/ribbon.vue';
import {computed, nextTick, onMounted, provide, reactive, ref, watch, inject} from '@vue/composition-api';
import {BButton, BIcon, BSidebar, BvModal, BRow, BFormSelect, BFormSelectOption} from 'bootstrap-vue';
import '@/shared/config/dayjs';
import {useGetters, useRouter, useStore} from '@u3u/vue-hooks';
import NotificationService from './shared/notification/notification-service';
import axios from 'axios';
import BlockUi from '@/core/block-ui/block-ui.vue';

const ALERT_HEADER = 'x-pwc-alert';
const ALERT_MESSAGE = 'x-pwc-params';
// import Home from '@/components/home.vue';
// import MenuService from '@/core/menu/menu-service';
// import Breadcrumb from '@/core/menu/breadcrumb.vue';

export default {
  name: 'app',
  components: {
    ribbon: Ribbon,
    BlockUi,
    // Home,
    // Breadcrumb,
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

    //麵包屑用的，目前已由eip那邊控制，所以這裡關閉
    // const menuService = inject<() => MenuService>('menuService')();

    const notificationService = new NotificationService(context.root);
    const isContentAlive = ref(true);

    const dynamicSizeForDev = () => {
      window.addEventListener('resize', e => {
        useStore().value.commit('setCurrentWidth', window.innerWidth);
        useStore().value.commit('setCurrentHeight', window.innerHeight);
        const isMobileDevice = currentWidth.value < mobileUpperLimit.value;
        const isPadDevice = currentWidth.value >= padLowerLimit.value && currentWidth.value < padUpperLimit.value;
        const isDeskTopDevice = currentWidth.value >= deskTopLowerLimit.value;
        useStore().value.commit('setMobileDevice', isMobileDevice);
        useStore().value.commit('setPadDevice', isPadDevice);
        useStore().value.commit('setDeskTopDevice', isDeskTopDevice);
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

    // bpm側邊攔，目前已由eip那邊控制，所以此方法先註解
    // const reload = () => {
    //   isContentAlive.value = false;
    //   nextTick(() => (isContentAlive.value = true));
    // };

    //麵包屑用的，目前已由eip那邊控制，所以這裡關閉
    // provide('menuService', menuService);

    provide<NotificationService>('notificationService', notificationService);
    provide<BvModal>('$bvModal', overrideBvModal(context.root.$bvModal));

    onMounted(() => {
      dynamicSizeForDev();
      lockInputTypeNumberWheelEvent();
    });

    return {
      isContentAlive,
      ...useGetters(['isLoading', 'keepAlivePage']),
      // reload
    };
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

.bpm_form_header {
  background-color: #66bfab;
  padding-top: 10px;
}

.bpm_background {
  background-color: #d3ede8;
}

.table-bordered thead th, .table-bordered thead td {
  border-bottom-width: 2px;
  background-color: #b0ded4;
}

</style>
