import lessThanOrEqualTo from '@/shared/validators/raw/less-than-or-equal-to';
import { formatDate } from '@/shared/date/minguo-calendar-utils';
import { Ref, unref } from '@vue/composition-api';

export default function (compareValue: number | string | Date | Ref<number> | Ref<string> | Ref<Date>, errorMessage?: (compareVal: number | string | Date) => string) {
  return {
    $validator: lessThanOrEqualTo(compareValue),
    $message: () => {
      let compareVal = unref(compareValue);
      if (compareVal instanceof Date) {
        compareVal = formatDate(compareVal, '/');
      }
      return errorMessage ? errorMessage(compareVal) : `輸入的值必須小於或等於 ${compareVal}`;
    },
  };
}