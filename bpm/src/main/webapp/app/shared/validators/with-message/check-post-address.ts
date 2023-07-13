import checkPostAddress from '@/shared/validators/raw/check-post-address';

/**
 * Check value when condition not null.
 * @param { string } givenValue
 * @param { * } condition
 */
export default function (givenValue) {
  return {
    $validator: checkPostAddress(givenValue),
    $message: ({ $params }) => {
      return `地址格式有誤(選擇縣市後，其他值未輸入)`;
    },
    $params: { givenValue },
  };
}
