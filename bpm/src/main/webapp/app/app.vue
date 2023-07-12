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
// import {
//   BButton,
//   BIcon,
//   BSidebar,
//   BvModal,
//   BButtonGroup,
//   BCard,
//   BCardBody,
//   BCol,
//   BCollapse,
//   BContainer,
//   BDropdown,
//   BDropdownDivider,
//   BDropdownItemButton,
//   BFormCheckbox,
//   BFormCheckboxGroup,
//   BFormInput,
//   BFormRadio,
//   BFormRadioGroup,
//   BFormRow,
//   BFormSelect,
//   BFormSelectOption,
//   BFormTextarea,
//   BInputGroup,
//   BInputGroupAppend,
//   BInputGroupText,
//   BRow,
//   BTableSimple,
//   BTbody,
//   BTd,
//   BThead,
//   BTr,
// } from 'bootstrap-vue';
import '@/shared/config/dayjs';

export default {
  name: 'app',
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'jhi-footer': JhiFooter,
    Home,
    Deal,
    // BButton,
    // BIcon,
    // BSidebar,
    // BButtonGroup,
    // BCard,
    // BCardBody,
    // BCol,
    // BCollapse,
    // BContainer,
    // BDropdown,
    // BDropdownDivider,
    // BDropdownItemButton,
    // BFormCheckbox,
    // BFormCheckboxGroup,
    // BFormInput,
    // BFormRadio,
    // BFormRadioGroup,
    // BFormRow,
    // BFormSelect,
    // BFormSelectOption,
    // BFormTextarea,
    // BInputGroup,
    // BInputGroupAppend,
    // BInputGroupText,
    // BRow,
    // BTableSimple,
    // BTbody,
    // BTd,
    // BThead,
    // BTr,
  },
  setup() {
    // const router = useRouter();
    // router.push({ name: 'Deal' });
  },
};
</script>
<style>
.app {
  background-color: white;
}

.sidebar {
  height: 100vh;
  width: 300px;
  background-color: white;
  float: left;
}

.main {
  padding: 10px;
  width: 100vw;
  height: 100vh;
  background-color: white;
}
</style>