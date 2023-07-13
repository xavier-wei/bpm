import notChinese from '@/shared/validators/raw/not-chinese';

/**
 * Check if area number is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default {
    $validator: notChinese,
    $message: '執行機關代碼不可輸入中文',
}