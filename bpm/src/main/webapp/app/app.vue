<template>
  <div class="app">
    <div class="sidebar">
      <!-- 侧边栏内容 -->
      <Home></Home>
    </div>
    <!-- <div class="main">
      <Deal></Deal>
    </div> -->
    <div class="main">
      <router-view></router-view>
    </div>
  </div>
</template>

<script lang="ts" >
import Ribbon from '../app/core/ribbon/ribbon.vue';
import JhiFooter from '../app/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '../app/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '../app/account/login-form/login-form.vue';
import Home from '../app/componet/home.vue';
import Deal from '../app/componet/deal.vue';
import { computed, nextTick, onMounted, provide, reactive, ref, watch, inject } from '@vue/composition-api';
import { BButton, BIcon, BSidebar, BvModal, BRow } from 'bootstrap-vue';
import '@/shared/config/dayjs';
import { useGetters, useRouter, useStore } from '@u3u/vue-hooks';



export default {
  name: 'app',
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'jhi-footer': JhiFooter,
    Home,
    Deal,
  },
  setup(prop, context) {
    // const router = useRouter();
    // router.push({ name: 'Deal' });
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
        useStore().value.commit('setMenuState', isDeskTopDevice);
      });
    };

    const lockInputTypeNumberWheelEvent = () => {
      document.addEventListener('wheel', e => {
        if (document.activeElement.type !== 'number') return;
        document.activeElement.blur();
      });
    };

    onMounted(() => {

      dynamicSizeForDev();
      lockInputTypeNumberWheelEvent();
    });

  },
};
</script>
<style scoped lang="scss">
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

.btn-link {
  border-radius: 10rem;
  background: #1aa4b7;
  text-align: center;
}


</style>
