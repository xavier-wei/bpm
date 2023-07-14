import { unref } from 'vue-demi';

/**
 * Check if tel extension is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return (value: any) => {
    if (value.length === 0) {
      return true;
    }
    const regex = new RegExp(/^\d{1,4}$/, 'g');
    if (givenValue) {
      return regex.test(value);
    } else {
      return true;
    }
  };
}
