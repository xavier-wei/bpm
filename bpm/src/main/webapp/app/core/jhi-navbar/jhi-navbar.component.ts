import { Component, Inject, Vue } from 'vue-property-decorator';

import EntitiesMenu from '@/entities/entities-menu.vue';

@Component({
  components: {
    'entities-menu': EntitiesMenu,
  },
})
export default class JhiNavbar extends Vue {
  @Inject('loginService')

  public version = 'v' + VERSION;
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValues = {};

  created() {}

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public logout(): Promise<any> {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    if (this.$route.path !== '/') {
      return this.$router.push('/');
    }
    return Promise.resolve(this.$router.currentRoute);
  }

  public openLogin(): void {
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {

    return this.hasAnyAuthorityValues[authorities] ?? false;
  }

  public get openAPIEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('api-docs') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }
}
