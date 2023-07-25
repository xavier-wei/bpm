import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';

const Home = () => import('../componet/home.vue');
const Deal = () => import('../componet/deal.vue');
const Pending = () => import('../componet/pending.vue');
const Deal3 = () => import('../componet/deal3.vue');
const Notify = () => import('../componet/notify.vue');
import account from './account';
import admin from './admin';
import entities from './entities';
import pages from './pages';
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
      props: true
    },
    {
      path: '/pending',
      name: 'pending',
      component: Pending,
      props: true
    },
    {
      path: '/deal3',
      name: 'deal3',
      component: Deal3,
      props: true
    },
    {
      path: '/notify',
      name: 'notify',
      component: Notify,
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
