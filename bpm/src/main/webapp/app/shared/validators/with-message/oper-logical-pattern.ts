import operLogicalPattern from '@/shared/validators/raw/oper-logical-pattern';

/**
 * Check if a value is greater than given value.
 * @param {Date | Number | Ref<Date> | Ref<Number>} givenValue
 * @returns {NormalizedValidator}
 */
export default function (givenValue) {
  return {
    $validator: operLogicalPattern(givenValue),
    $message: ({ $params }) => {
      const value = $params.givenValue;
      return `${value}`;
    },
    $params: { givenValue },
  };
}
