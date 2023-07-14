import { unref } from 'vue-demi';

/**
 * Check if array length is greater than given value.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */
export default function (givenValue) {
  return (value: any[]) => {
    if (value == null) {
      return false;
    }
    const compareValue = unref(value.length);
    const greaterValue = unref(givenValue);
    return Number(compareValue) > Number(greaterValue);
  };
}
