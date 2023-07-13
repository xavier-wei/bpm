import notNumber from '@/shared/validators/raw/not-number';

/**
 * Check if area number is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default {
    $validator: notNumber,
    $message: '請輸入整數',
}