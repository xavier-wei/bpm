import arrayLengthGreaterThan from '@/shared/validators/raw/array-length-greater-than';

/**
 * Check if a value is greater than given value.
 * @param {Date | Number | Ref<Date> | Ref<Number>} givenValue
 * @returns boolean
 */
export default function (givenValue) {
  return {
    $validator: arrayLengthGreaterThan(givenValue),
    $message: ({ $params }) => {
      const value = $params.givenValue;
      return `選擇的項目數量必須大於 ${value}`;
    },
    $params: { givenValue },
  };
}
