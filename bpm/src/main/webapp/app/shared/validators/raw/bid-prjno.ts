import { helpers } from '@vuelidate/validators';

export default function (prjno: string): boolean {
  return helpers.regex(/^[-a-zA-Z0-9]+$/)(prjno);
}
