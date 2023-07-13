import { helpers } from '@vuelidate/validators';

export default function (mobile: string): boolean {
  return helpers.regex(/^09/, 'g')(mobile);
}