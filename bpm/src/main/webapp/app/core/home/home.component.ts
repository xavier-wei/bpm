import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';

@Component
export default class Home extends Vue {
  @Inject('loginService')

  public openLogin(): void {
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account?.login ?? '';
  }
}
