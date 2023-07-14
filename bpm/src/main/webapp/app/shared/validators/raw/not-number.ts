/**
 * if is chinese is inValid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

import { helpers } from "@vuelidate/validators";

 export default function (value) {
  return helpers.regex(/^\d*$/)(value);
}