import { unref } from 'vue-demi';

export const minguoYearMonth = value => {
  value = unref(value);
  if (value === undefined || value === null || value === '') {
    return true;
  }
  if (value.length !== 5) {
    return false;
  }
  if (!(!isNaN(value) && !isNaN(parseInt(value)))) {
    return false;
  }

  const regex = /^([0-1][0-9]{2})(1[0-2]|0[1-9])$/;

  const result = regex.exec(value);

  if (result === null) {
    return false;
  } else if (result[1] === '000') {
    return false;
  }

  return true;
};
