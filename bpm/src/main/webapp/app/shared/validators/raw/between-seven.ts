import { unref } from 'vue-demi';

/**
 * Check if absolute diff is between 7.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return (value: any) => {
    const compareValue = unref(value);
    const lessValue = unref(givenValue);
    if (compareValue && lessValue) {
      return Math.abs(Number(compareValue) - Number(lessValue)) < 7;
    }
    return false;
  };
}
