import { useRouter, useStore } from '@u3u/vue-hooks';

export const router = useRouter().router;

export const route = useRouter().route.value;

export function navigateByNameAndParams(name, params) {
  router.push({
    name: name,
    params: params,
  });
}

export function handleBack(params?) {
  const fromName = useStore().value.getters.routeData.fromName;
  navigateByNameAndParams(fromName, params);
}

function createParamMap(params?: object) {
  if (params) {
    const map = {};
    Object.keys(params).forEach((key: string) => {
      map[key] = JSON.stringify(params[key]);
    });
    return map;
  }
  return null;
}

export function goPreviousPage() {
  router.go(-1);
}
