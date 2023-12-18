import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';

const Home = () => import('@/components/home.vue');
const Deal = () => import('@/components/deal.vue');

import bpmRouter from "@/router/bpmRouter";
Vue.use(Router);

// prettier-ignore
const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'deal',
      component: Deal,
      meta: {
        functionId: 'deal',
      },
      props: true
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      props: true
    },
    ...bpmRouter
  ]
});

export default router;
