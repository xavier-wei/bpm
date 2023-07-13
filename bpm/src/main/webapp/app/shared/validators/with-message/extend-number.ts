import extendNumber from '@/shared/validators/raw/extend-number';
import { Ref } from 'vue-demi';

/**
 * Check if tel extension is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return {
    $validator: extendNumber(givenValue),
    $message: '請輸入正確分機格式。(不輸入或1~4個數字)。',
    $params: { givenValue },
  };
}
