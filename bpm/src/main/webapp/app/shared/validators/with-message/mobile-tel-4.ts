import mobileTel4 from '@/shared/validators/raw/moblie-tel-4';

/**
 * Check if mobile tel first 4 num is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return {
    $validator: mobileTel4(givenValue),
    $message: '請輸入正確格式。例如：0933。',
    $params: { givenValue },
  };
}
