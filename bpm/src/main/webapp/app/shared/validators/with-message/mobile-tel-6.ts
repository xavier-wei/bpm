import mobileTel6 from '@/shared/validators/raw/moblie-tel-6';

/**
 * Check if mobile tel last 6 num is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return {
    $validator: mobileTel6(givenValue),
    $message: '請輸入正確格式。例如：112233。',
    $params: { givenValue },
  };
}
