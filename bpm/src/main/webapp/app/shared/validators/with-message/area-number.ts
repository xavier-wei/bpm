import areaNumber from '@/shared/validators/raw/area-number';
import { Ref } from 'vue-demi';

/**
 * Check if area number is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return {
    $validator: areaNumber(givenValue),
    $message: '請輸入正確格式。例如：02。',
    $params: { givenValue },
  };
}
