import { trim as _trim, includes as _includes } from 'lodash';

export default function (confNumber: string): boolean {
  let valid = false;
  confNumber = _trim(confNumber);
  if (_includes(confNumber, '電話')
    || _includes(confNumber, '通知')
    || _includes(confNumber, '電子郵件')
    || _includes(confNumber, '簽')
    || _includes(confNumber, '傳真')
    || _includes(confNumber, 'mail')
  ) {
    valid = false;
  } else if (_includes(confNumber, '@')
    || _includes(confNumber, '%')
    || _includes(confNumber, '○')
    || _includes(confNumber, 'XXX')
    || _includes(confNumber, '?')
    || _includes(confNumber, '*')
  ) {
    valid = false;
  } else if (allChinese(confNumber)) {
    valid = false;
  } else {
    valid = true;
  }
  return valid;
}

function allChinese(confNumber: string): boolean {
  return /^[\u4E00-\u9FFF\uD800-\uDFFF]+$/.test(confNumber);
}
