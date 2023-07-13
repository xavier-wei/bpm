import lessThanOneYear from '@/shared/validators/raw/less-than-one-year';
import { formatDate } from '@/shared/date/minguo-calendar-utils';
import { Ref } from 'vue-demi';

/**
 * Check if a value is greater than given value.
 * @param {Date | Number | Ref<Date> | Ref<Number>} givenValue
 * @returns {NormalizedValidator}
 */
export default function (givenValue: Date | string | Ref<Date> | Ref<string>) {
  return {
    $validator: lessThanOneYear(givenValue),
    $message: ({ $params }: any) => {
      let value = $params.givenValue;
      if ($params.givenValue instanceof Date) {
        value = formatDate($params.givenValue);
      }
      return `輸入時間區間必須小於一年`;
    },
    $params: { givenValue },
  };
}
