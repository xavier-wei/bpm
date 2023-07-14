import { unref } from 'vue-demi';

/**
 * Check if password is conformed to GPO standard.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return (value: any) => {
    const regex = new RegExp(
      /^((?=.{8,}$)(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).*|(?=.{8,}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!\u0022#$%&'()*+,./:;<=>?@[\]\^_`{|}~-]).*)/,
      'g'
    );
    if (givenValue) {
      return regex.test(value);
    } else {
      return false;
    }
  };
}
