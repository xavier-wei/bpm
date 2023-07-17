import Vue from 'vue';

export default async function (): Promise<string> {

  const keycloak = Vue.prototype.$keycloak;

  if (keycloak.authenticated) {
    try {
      await keycloak.keycloak.updateToken(70);
    } catch (error) { }
    const token = keycloak.token as string;
    return token;
  }

}
