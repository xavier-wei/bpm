/**
 * Check if mobile tel last 6 num is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return (value: any) => {
    if (value.length === 0) {
      return true;
    }
    const regex = new RegExp(/^[0-9]{6}$/, 'g');
    if (givenValue) {
      return regex.test(value);
    } else {
      return false;
    }
  };
}
