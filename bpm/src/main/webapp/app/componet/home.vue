<template>
  <div>
    <ul>
      <li>
        <router-link to="/">L410-共用系統使用者帳號申請單</router-link>
      </li>
      <li>
        <router-link to="/deal">L414-網路服務連結申請單</router-link>
      </li>
      <li>
        <router-link to="/deal2">待處理</router-link>
      </li>
      <li>
        <router-link to="/deal3">通知</router-link>
      </li>
      <li>
        <router-link to="/contact">申請</router-link>
      </li>
      <li>
        <router-link to="/contact">處理中</router-link>
      </li>
      <li>
        <router-link to="/">處理過</router-link>
      </li>
    </ul>
    <!-- <ul>
      <li>
        <router-link class="list-group-item" active-class="active" to="/searchLog">批次服務查詢</router-link>
      </li>
     
      <li>
        <router-link class="list-group-item" active-class="active" to="/iwg-host-list">註冊監控主機</router-link>
      </li>
      <li>
        <router-link class="list-group-item" active-class="active" to="/iwg-host-target">主機監控設定</router-link>
      </li>
     
    </ul> -->
  </div>
</template>

<script lang="ts">
import { ref, toRef, Ref, toRefs } from 'vue';

export default {
  name: 'home',
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