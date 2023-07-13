import { unref } from 'vue-demi';
import dayjs from 'dayjs';

/**
 * Check begin date less than end date.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */
export default function (givenValue) {
  return value => {
    const compareValue = unref(value);
    const lessValue = unref(givenValue);
    if (compareValue && lessValue) {
      if (compareValue instanceof Date && lessValue instanceof Date) {
        return dayjs(lessValue).isBefore(compareValue);
      }
      return Number(lessValue) < Number(compareValue);
    }
    return true;
  };
}
