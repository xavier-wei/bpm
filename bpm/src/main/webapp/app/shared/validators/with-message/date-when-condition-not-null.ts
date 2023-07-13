import whenConditionNotNull from '@/shared/validators/raw/when-condition-not-null';

/**
 * Check date when condition not null.
 * @param { string } givenValue
 */
export default function (givenValue) {
  return {
    $validator: whenConditionNotNull(givenValue),
    $message: ({ $params }) => {
      return `歷程:狀態為新增或修改時，請選擇起訖日期`;
    },
    $params: { givenValue },
  };
}
