import { helpers } from '@vuelidate/validators';

export default function (tel: string): boolean {
  return helpers.regex(/^[0-9$&+,:;=?@#|'<>.^*()%!-]+$/)(tel);
}
