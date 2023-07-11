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
const Deal2 = () => import('../componet/deal2.vue');
import account from './account';
import admin from './admin';
import entities from './entities';
import pages from './pages';
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
      path: '/deal2',
      name: 'deal2',
      component: Deal2,
      props: true
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      props: true
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      props: true
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      props: true
    },

    
    ...account,
    ...admin,
    entities,
    ...pages
  ]
});

export default router;
