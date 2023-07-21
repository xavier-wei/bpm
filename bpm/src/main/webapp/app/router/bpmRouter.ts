const L414Apply =() => import('@/componet/l414Apply.vue');
const Appendix =() => import('@/componet/appendix.vue');
const FlowChart =() => import('@/componet/FlowChart.vue');
const L410Apply =() => import('@/componet/l410Apply.vue');

export default [
  {
    path: '/l414Apply',
    name: 'L414Apply',
    component: L414Apply,
  },
  {
    path: '/appendix',
    name: 'Appendix',
    component: Appendix,
  },
  {
    path: '/flowChart',
    name: 'FlowChart',
    component: FlowChart,
  },
  {
    path: '/l410Apply',
    name: 'L410Apply',
    component: L410Apply,
  },
];
