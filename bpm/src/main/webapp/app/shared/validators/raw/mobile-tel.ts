import { helpers } from '@vuelidate/validators';

export default function (tel: string): boolean {
  return helpers.regex(/^[09]{2}[0-9]{2}[-]{1}[0-9]{6}$/)(tel);
}