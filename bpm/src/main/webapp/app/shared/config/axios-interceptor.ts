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

    const functionId = useStore().value.getters.currentFunctionId;
    if (functionId) {
      config.headers['X-FUNCTION-ID'] = functionId;
    }

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
    axios.interceptors.response.use(
      response => {
        return response;
      },
      error => {
        if (error.response === undefined || error.response === null) {
          return Promise.reject(error);
        }
        if (
          error.request.responseType === 'blob' &&
          error.response.data instanceof Blob &&
          error.response.data.type &&
          error.response.data.type.toLowerCase().indexOf('json') != -1
        ) {
          return new Promise((resolve, reject) => {
            const reader: any = new FileReader();
            reader.onload = () => {
              error.response.data = JSON.parse(reader.result);
              resolve(Promise.reject(error));
            };

            reader.onerror = () => {
              reject(error);
            };

            reader.readAsText(error.response.data);
          });
        }
        return Promise.reject(error);
      }
    );
  }
};

export { onRequestSuccess, setupAxiosInterceptors };
