import telNumber from '@/shared/validators/raw/tel-number';
import { Ref } from 'vue-demi';

/**
 * Check if tel number is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return {
    $validator: telNumber(givenValue),
    $message: '請輸入正確格式。例如：22369896。',
    $params: { givenValue },
  };
}
