import betweenSeven from '@/shared/validators/raw/between-seven';
import { Ref } from 'vue-demi';

/**
 * Check if the two param is between 7.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: number | Ref<number>) {
  return {
    $validator: betweenSeven(givenValue),
    $message: '區間必須介於7年內',
    $params: { givenValue },
  };
}
