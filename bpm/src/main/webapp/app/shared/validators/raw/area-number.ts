/**
 * Check if tel number is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return (value: any) => {
    if (value.length === 0) {
      return true;
    }
    const regex = new RegExp(/^(0[2-8]\d*)$/, 'g');
    if (givenValue) {
      return regex.test(value);
    } else {
      return false;
    }
  };
}
