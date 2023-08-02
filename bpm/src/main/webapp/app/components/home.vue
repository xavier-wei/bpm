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
