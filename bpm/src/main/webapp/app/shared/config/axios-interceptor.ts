import axios, {AxiosResponse} from 'axios';
import { useStore } from '@u3u/vue-hooks';

const TIMEOUT = 1000000;
const onRequestSuccess = config => {
  const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
  if (token) {
    if (!config.headers) {
      config.headers = {};
    }
    config.headers.Authorization = `Bearer ${token}`;
  }
  config.timeout = TIMEOUT;
  config.url = `${SERVER_API_URL}${config.url}`;
  useStore().value.commit('setLoadingStatus', { status: true, url: config.url });
  return config;
};
const setupAxiosInterceptors = (onUnauthenticated, onServerError) => {
  const onResponseError = err => {
    const status = err.status || err.response.status;
    if (status === 403 || status === 401) {
      return onUnauthenticated(err);
    }
    if (status >= 500) {
      return onServerError(err);
    }
    useStore().value.commit('setLoadingStatus', { status: false, url: err.config ? err.config.url : '' });
    return Promise.reject(err);
  };

  if (axios.interceptors) {
    axios.interceptors.request.use(onRequestSuccess);
    axios.interceptors.response.use((response: AxiosResponse) => {
      useStore().value.commit('setLoadingStatus', { status: false, url: response.config.url });
      return response;
    }, onResponseError);
  }
};

export { onRequestSuccess, setupAxiosInterceptors };
