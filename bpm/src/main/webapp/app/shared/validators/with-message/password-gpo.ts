import passwordGpo from '@/shared/validators/raw/password-gpo';
import { Ref } from 'vue-demi';

/**
 * Check if password is conformed to GPO standard.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return {
    $validator: passwordGpo(givenValue),
    $message:
      // '密碼複雜度須符合GPO標準。' +
      // '至少 8 個字元，' +
      // '至少有以下四個內容的其中三個：' +
      // '小寫字母、' +
      // '大寫字母、' +
      // '數字、' +
      // '特殊符號。',
      '密碼須符合複雜度標準。密碼至少 8 個字元，且有以下四個內容的其中三個：小寫字母、大寫字母、數字、特殊符號。',
    $params: { givenValue },
  };
}
