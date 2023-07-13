import { Ref, unref } from 'vue-demi';
import dayjs from 'dayjs';

/**
 * Check if value is less than given value.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */
export default function (givenValue: number | string | Date | Ref<number> | Ref<string> | Ref<Date>) {
  return (value: number | string | Date) => {
    const compareValue = unref(value);
    const lessValue = unref(givenValue);
    if (compareValue instanceof Date && lessValue instanceof Date) {
      return !compareValue || !lessValue || dayjs(compareValue).isBefore(lessValue);
    }
    return !compareValue || !lessValue || compareValue < lessValue;
  };
}
