const l414Apply =() => import('@/components/l414Apply.vue');
const l410Apply =() => import('@/components/l410Apply.vue');
const appendix =() => import('@/components/appendix.vue');
const flowChart =() => import('@/components/flowChart.vue');

const breadcrumb =() => import('../core/menu/breadcrumb.vue');

export default [
  {
    path: '/l410Apply',
    name: 'l410Apply',
    component: l410Apply,
    meta: {
      functionId: 'l410Apply',
    },
    props: true,
  },
  {
    path: '/l414Apply',
    name: 'l414Apply',
    component: l414Apply,
    meta: {
      functionId: 'l414Apply',
    },
    props: true,
  },
  {
    path: '/appendix',
    name: 'appendix',
    component: appendix,
    meta: {
      functionId: 'appendix',
    },
    props: true,
  },
  {
    path: '/flowChart',
    name: 'flowChart',
    component: flowChart,
    meta: {
      functionId: 'flowChart',
    },
    props: true,
  },
  {
    path: '/breadcrumb',
    name: 'breadcrumb',
    component: breadcrumb,
    meta: {
      functionId: 'breadcrumb',
    },
    props: true,
  },
];
