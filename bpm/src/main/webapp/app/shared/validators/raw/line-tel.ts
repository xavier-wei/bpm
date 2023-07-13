import { helpers } from '@vuelidate/validators';

export default function (tel: string): boolean {
  return helpers.regex(/^0[2-8][2-9]{0,3}-[2-9][0-9]{4,7}(|#[0-9]+)$/)(tel);
}