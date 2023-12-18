const Pending = () => import('@/components/pending.vue');
const Notify = () => import('@/components/notify.vue');
const L414Query = () => import('@/components/l414/l414Query.vue');
const L410Query = () => import('@/components/l410/l410Query.vue');
const l414Apply = () => import('@/components/l414/l414Apply.vue');
const l410Apply = () => import('@/components/l410/l410Apply.vue');
const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');
const signatureBmodel = () => import('@/components/signatureBmodel.vue');
const signerList = () => import('@/components/signerList.vue');
const userSys = () => import('@/components/userSys.vue');
const breadcrumb = () => import('@/core/menu/breadcrumb.vue');
const l414Edit = () => import('@/components/l414/l414Edit.vue');
const l410Edit = () => import('@/components/l410/l410Edit.vue');
const errandBmodel = () => import('@/components/errandBmodel.vue');
const setSupervisor = () => import('@/components/setSupervisor.vue');
const supervisorAdmin = () => import('@/components/supervisorAdmin.vue');

export default [
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
  {
    path: '/l414Edit',
    name: 'l414Edit',
    component: l414Edit,
    meta: {
      functionId: 'l414Edit',
    },
    props: true,
  },
  {
    path: '/l410Edit',
    name: 'l410Edit',
    component: l410Edit,
    meta: {
      functionId: 'l410Edit',
    },
    props: true,
  },
  {
    path: '/signerList',
    name: 'signerList',
    component: signerList,
    meta: {
      functionId: 'signerList',
    },
    props: true,
  },
  {
    path: '/signatureBmodel',
    name: 'signatureBmodel',
    component: signatureBmodel,
    meta: {
      functionId: 'signatureBmodel',
    },
    props: true,
  },
  {
    path: '/userSys',
    name: 'userSys',
    component: userSys,
    meta: {
      functionId: 'userSys',
    },
    props: true,
  },
  {
    path: '/errandBmodel',
    name: 'errandBmodel',
    component: errandBmodel,
    meta: {
      functionId: 'errandBmodel',
    },
    props: true,
  },
  {
    path: '/setSupervisor',
    name: 'setSupervisor',
    component: setSupervisor,
    meta: {
      functionId: 'setSupervisor',
    },
    props: true,
  },
  {
    path: '/supervisorAdmin',
    name: 'supervisorAdmin',
    component: supervisorAdmin,
    meta: {
      functionId: 'supervisorAdmin',
    },
    props: true,
  },
];

