import { helpers } from '@vuelidate/validators';

export default function (id: string): boolean {
  //若沒輸入身分證不會報錯
  if (id === undefined || id === null || id === '') {
    return true;
  } else if (/^[a-zA-Z]{2}/.test(id)) {
    //舊式居留證(FA12345689)
    // id = id.replace(/\s+/g, '');
    if (!/^[a-zA-Z]{1}[a-zA-Z]{1}[0-9]{8}$/.test(id)) {
      return false;
    }

    const convert = 'ABCDEFGHJKLMNPQRSTUVXYWZIO';
    const weights = [1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1];

    id = String(convert.indexOf(id[0]) + 10) + String((convert.indexOf(id[1]) + 10) % 10) + id.slice(2);

    let checkSum = 0;
    for (let i = 0; i < id.length; i++) {
      const c = parseInt(id[i]);
      const w = weights[i];
      checkSum += c * w;
    }

    return !(checkSum % 10);
  } else {
    id = id.replace(/\s+/g, '');
    if (!/^[a-zA-Z]{1}[1-28-9]{1}[0-9]{8}$/.test(id)) {
      return false;
    }

    const locationMap = [10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33];
    /* A, B, C, D, E, F, G, H, I, J, K, L, M,
       N, O, P, Q, R, S, T, U, V, W, X, Y, Z */
    const digit = locationMap[id.toUpperCase().charCodeAt(0) - 'A'.charCodeAt(0)];
    let checksum = 0;
    checksum += Math.floor(digit / 10);
    checksum += (digit % 10) * 9;
    /* i: index; p: permission value */
    /* this loop sums from [1] to [8] */
    /* permission value decreases */
    for (let i = 1, p = 8; i <= 8; i++, p--) {
      checksum += parseInt(id.charAt(i)) * p;
    }
    // add the last number
    checksum += parseInt(id.charAt(9));
    return !(checksum % 10);
  }
}
