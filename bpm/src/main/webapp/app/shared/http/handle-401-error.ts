import { AxiosError } from 'axios';

export function handle401Error(error: AxiosError) {
  if (error && error.response && (error.response.status === 401 || error.response.status === 401)) {
    sessionStorage.setItem('loginErrorMessage', '您登入已逾時或無使用權限，已登出並導回首頁。');
    window.sessionStorage.removeItem('token');
    window.localStorage.removeItem('token');
    window.location.replace(
      `${process.env.KEYCLOAK_URL}/realms/${process.env.KEYCLOAK_REALM}/protocol/openid-connect/logout?redirect_uri=${process.env.WEB_URL}`
    );
    return;
  }
}
