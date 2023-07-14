/**
 * Check if mobile tel first 4 num is valid.
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */

export default function (givenValue: any) {
  return (value: any) => {
    console.log('givenValue：',givenValue)
    console.log('value：',value)
    if (value.length === 0) {
      return true;
    }
    const regex = new RegExp(/^09[0-9]{2}$/, 'g');
    if (givenValue) {
      return regex.test(value);
    } else {
      return false;
    }
  };
}
