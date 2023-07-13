/**
 * if is chinese is inValid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

import { helpers } from "@vuelidate/validators";

 export default function (value) {
  return helpers.regex(/[^\u4e00-\u9fa5]/)(value ? value.replace(/\s/g, '') : '');
  // return (value: any) => {
  //   if (value.length === 0) {
  //     return true;
  //   }
  //   const regex = new RegExp(/[^\u4e00-\u9fa5]/);
  //     return regex.test(value);
  // };
}