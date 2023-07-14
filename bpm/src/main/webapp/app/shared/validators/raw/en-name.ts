import { helpers } from '@vuelidate/validators';

export default function (enName: string): boolean {
  //return helpers.regex(/^[a-zA-Z]+[,]{1}[a-zA-Z]+[-]{1}[a-zA-Z]+$/)(enName ? enName.replace(/\s/g, '') : '');
  return helpers.regex(/^[a-zA-Z]+[,]{1}[a-zA-Z]+[-]{0,1}[a-zA-Z]*$/)(enName ? enName.replace(/\s/g, '') : '');

}
 