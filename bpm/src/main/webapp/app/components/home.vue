<template>
  <div>
    <ul>
      <li>
        <router-link to="/l410Query">L410-共用系統使用者帳號申請單</router-link>
      </li>
      <li>
        <router-link to="/l414Query">L414-網路服務連結申請單</router-link>
      </li>
      <li>
        <router-link to="/pending">待處理表單</router-link>
      </li>
      <li>
        <router-link to="/notify">表單查詢</router-link>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { BFormSelect, BFormSelectOption } from 'bootstrap-vue';
import { reactive, ref, watch } from '@vue/composition-api';
import { useStore } from '@u3u/vue-hooks';

export default {
  name: 'home',
  components: {
    'b-form-select': BFormSelect,
    'b-form-select-option': BFormSelectOption,
  },
  setup() {
    const userData = ref('');

    const options = reactive({
      userOptions: [
        { value: 'ApplyTester', text: '申請人' },
        { value: 'ChiefTester', text: '科長' },
        { value: 'DirectorTester', text: '主管' },
        { value: 'InfoTester', text: '資推' },
      ],
    });

    watch(userData, () => {
      console.log('userData::', userData.value);
      useStore().value.commit('setUserData', { user: userData.value });
    });

    return {
      options,
      userData,
    };
  },
};
</script>

<style scoped>
.app {
  display: flex;
}

.sidebar {
  width: 200px;
  background-color: #f0f0f0;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 10px;
}

.sidebar li a {
  text-decoration: none;
  color: #333;
}

.sidebar li a:hover {
  color: #000;
}
</style>
