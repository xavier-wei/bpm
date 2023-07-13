import { Ref, unref } from 'vue-demi';
import dayjs from 'dayjs';

/**
 * Check if value is greater than given value.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */
export default function (givenValue: number | string | Date | Ref<number> | Ref<string> | Ref<Date>) {
  return (value: any) => {
    const compareValue = unref(value);
    const greaterValue = unref(givenValue);
    if (compareValue instanceof Date && greaterValue instanceof Date) {
      return !compareValue || !greaterValue || dayjs(compareValue).isAfter(greaterValue);
    }
    return !compareValue || !greaterValue || compareValue > greaterValue;
  };
}
