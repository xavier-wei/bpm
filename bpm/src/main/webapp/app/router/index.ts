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
const Pending = () => import('@/components/pending.vue');
const Notify = () => import('@/components/notify.vue');
const L414Query = () => import('@/components/l414/l414Query.vue');
const L410Query = () => import('@/components/l410/l410query.vue');
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
      path: '/pending',
      name: 'pending',
      component: Pending,
      meta: {
        functionId: 'pending',
      },
      props: true
    },
    {
      path: '/notify',
      name: 'notify',
      component: Notify,
      meta: {
        functionId: 'notify',
      },
      props: true
    },
    {
      path: '/l414Query',
      name: 'l414Query',
      component: L414Query,
      meta: {
        functionId: 'l414Query',
      },
      props: true
    },
    {
      path: '/l410Query',
      name: 'l410Query',
      component: L410Query,
      meta: {
        functionId: 'l410Query',
      },
      props: true
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      props: true
    },
    // ...account,
    // ...admin,
    // entities,
    // ...pages,
    ...bpmRouter
  ]
});

export default router;
