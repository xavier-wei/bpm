import whenConditionNotNull from '@/shared/validators/raw/when-condition-not-null';

/**
 * Check value when condition not null.
 * @param { string } givenValue
 * @param { * } condition
 */
export default function (givenValue, condition) {
  return {
    $validator: whenConditionNotNull(givenValue, condition),
    $message: ({ $params }) => {
      return `請輸入或選擇欄位值`;
    },
    $params: { givenValue },
  };
}
