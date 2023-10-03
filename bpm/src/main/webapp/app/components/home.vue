<template>
  <div>
    <ul>
      <li>
        <router-link class="dropdown-item"  :to="{ path: '/l410Query', params: { isNotKeepAlive: true } }" v-slot="{ route }">
          <button @click="navigate(route)" role="link">L410-共用系統使用者帳號申請單</button>
        </router-link>
      </li>
      <li>
        <router-link class="dropdown-item"  :to="{ path: '/l414Query', params: { isNotKeepAlive: true } }" v-slot="{ route }">
          <button @click="navigate(route)" role="link">L414-網路服務連結申請單</button>
        </router-link>
      </li>
      <li>
        <router-link class="dropdown-item"  :to="{ path: '/pending', params: { isNotKeepAlive: true } }" v-slot="{ route }">
          <button @click="navigate(route)" role="link">待處理表單</button>
        </router-link>
      </li>
      <li>
        <router-link class="dropdown-item"  :to="{ path: '/notify', params: { isNotKeepAlive: true } }" v-slot="{ route }">
          <button @click="navigate(route)" role="link">表單查詢</button>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import {useGetters, useRouter, useStore} from '@u3u/vue-hooks';

export default {
  name: 'home',
  setup(props, context) {

    function navigate(route) {
      useStore().value.commit('clearKeepAlivePages');
      navigateFunc(route.path);
    }

    function navigateFunc(url) {
      useRouter()
        .router.push(url)
        .catch(() => {
          context.emit('reloadContent');
        });
    }

    return {
      navigate,
    }


  },
};
</script >

<style lang="scss" scoped>
//.app {
//  display: flex;
//}
//
//.sidebar {
//  width: 200px;
//  background-color: #f0f0f0;
//}
//
//.sidebar ul {
//  list-style: none;
//  padding: 0;
//  margin: 0;
//}
//
//.sidebar li {
//  padding: 10px;
//}
//
//.sidebar li a {
//  text-decoration: none;
//  color: #333;
//}
//
//.sidebar li a:hover {
//  color: #000;
//}
</style>
