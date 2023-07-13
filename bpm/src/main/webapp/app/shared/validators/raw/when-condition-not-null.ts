import { unref } from 'vue-demi';

/**
 * Check begin date less than end date.
 * @param {*} givenValue
 * @param {*} condition
 * @return {function(*=): boolean}
 */
export default function (givenValue, condition?) {
  return value => {
    const targetValue = unref(value);
    const sourceValue = unref(givenValue);
    let conditionValue = undefined;
    if (condition !== undefined && condition !== null) {
      conditionValue = unref(condition);
    }
    if (condition === undefined && sourceValue) {
      return targetValue !== null && targetValue != undefined && targetValue !== '';
    }
    if (condition && sourceValue === conditionValue) {
      return targetValue !== null && targetValue != undefined && targetValue !== '';
    }
    return true;
  };
}
