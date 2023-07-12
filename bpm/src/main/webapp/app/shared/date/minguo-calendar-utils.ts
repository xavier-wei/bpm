import { computed, ComputedRef, reactive, unref,Ref, isRef  } from '@vue/composition-api';
import { isDate as _isDate } from 'lodash';
export function computedDate(value: Date | Ref<Date>): ComputedRef<string> {
  return computed({
    get: () => formatDate(value),
    set: (text: string) => parseDate(text, value),
  });
}

export function computedDateReactive(state: any, field: string): ComputedRef<string> {
  return computed({
    get: () => formatDate(state[field]),
    set: (text: string) => parseDate(text, state, field),
  });
}

export function formatDateYYYYMMDD(value: Ref<Date> | Date | null, delimiter?: string): string {
  const date: Date | null = unwrap(value);
  if (date) {
    const year = date.getFullYear().toString().padStart(3, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    if (delimiter) {
      return `${year}${delimiter}${month}${delimiter}${day}`;
    }
    return `${year}${month}${day}`;
  }
  return '';
}

export function formatDate(value: Ref<Date> | Date | null, delimiter?: string): string {
  const date: Date | null = unwrap(value);
  if (date) {
    const year =
      date.getFullYear() < 1911
        ? '-' + Math.abs(date.getFullYear() - 1911).toString()
        : (date.getFullYear() - 1911).toString().padStart(3, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    if (delimiter) {
      return `${year}${delimiter}${month}${delimiter}${day}`;
    }
    return `${year}${month}${day}`;
  }
  return '';
}

export function formatDateTime(value: Ref<Date> | Date | null, delimiter?: string): string {
  const date: Date | null = unwrap(value);
  if (date) {
    return (
      formatDate(value, delimiter) +
      ' ' +
      new Intl.DateTimeFormat('en', {
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false,
      }).format(date)
    );
  }
  return '';
}

export function parseDate(text: string, reference: Ref<Date> | any, field?: string): void {
  let date = null;
  let textString = text;
  if (text.indexOf('/') > 0) {
    textString = text.replace('/', '').replace('/', '');
  }
  if (textString.length === 7) {
    const year = textString.substring(0, 3);
    const month = textString.substring(3, 5);
    const day = textString.substring(5);
    date = new Date(Number(year) + 1911, Number(month) - 1, Number(day));
  }
  if (isRef(reference)) {
    reference.value = date;
  } else if (field) {
    reference[field] = date;
  } else {
    throw new Error('Neither reference is a Ref, nor field is given.');
  }
}

export function parseRocDate(text: string, delimiter?: string): Date {
  if (!text) return null;
  if (!delimiter) {
    delimiter = '/';
  }
  const dateString = formatMinguoDate(text, delimiter).replace('/', '').replace('/', '');
  const year = dateString.substring(0, 4);
  const month = dateString.substring(4, 6);
  const day = dateString.substring(6);
  return new Date(Number(year), Number(month) - 1, Number(day));
}

export function dateFormatter(value: Date | Ref<Date>, key: any, item: any) {
  return formatDate(value, '/');
}

export function dateTimeFormatter(value: Date | Ref<Date>, key: any, item: any) {
  return formatDateTime(value, '/');
}

export function useMinguoDate() {
  // @ts-ignore
  const state = reactive({
    date: null,
    dateText: computed({
      get: (): string => formatDate(state.date),
      set: (text: string): void => parseDate(text, state, 'date'),
    }),
  });
  return state;
}

export function formatMinguoDate(value: Ref<string> | string | null, delimiter?: string) {
  let date: string | null;
  if (isRef(value)) {
    date = value.value;
  } else {
    date = value;
  }
  if (!delimiter) {
    delimiter = '/';
  }
  if (date) {
    const dateArray = date.split(delimiter);
    if (dateArray.length === 1) {
      dateArray[0] = date.substr(0, 3);
      dateArray[1] = date.substr(3, 2);
      dateArray[2] = date.substr(5, 2);
    }
    const year = (Number(dateArray[0]) + 1911).toString().padStart(4, '0');
    const month = dateArray[1].toString().padStart(2, '0');
    const day = dateArray[2].toString().padStart(2, '0');
    return `${year}${delimiter}${month}${delimiter}${day}`;
  }
  return '';
}

export function formatDateString(value: Date) {
  return (
    value.getFullYear().toString().padStart(4, '0') +
    '-' +
    (value.getMonth() + 1).toString().padStart(2, '0') +
    '-' +
    value.getDate().toString().padStart(2, '0')
  );
}

function unwrap(value: Ref<Date> | Date | null): Date | null {
  let date: Date | null;
  if (isRef(value)) {
    date = value.value;
  } else {
    date = value;
  }
  return date;
}

export function formatToString(value: Ref<Date> | Date | string | null, delimiter?: string, splitStr?: string, isMinguo = true): string {
  let year: string;
  let month: string;
  let day: string;
  if (typeof value !== 'string') {
    const date: Date | null = unref(value);
    if (date) {
      year = isMinguo ? (date.getFullYear() - 1911).toString().padStart(3, '0') : date.getFullYear().toString().padStart(4, '0');
      month = (date.getMonth() + 1).toString().padStart(2, '0');
      day = date.getDate().toString().padStart(2, '0');
    } else {
      return '';
    }
  } else {
    const valueArr = value.split(splitStr);
    if (valueArr[0].length === 4 && isMinguo) {
      valueArr[0] = (Number(valueArr[0]) - 1911).toString();
    } else if (valueArr[0].length !== 4 && !isMinguo) {
      valueArr[0] = (Number(valueArr[0]) + 1911).toString();
    }
    year = valueArr[0];
    month = valueArr[1];
    day = valueArr[2];
  }

  if (delimiter) {
    return `${year}${delimiter}${month}${delimiter}${day}`;
  }
  return `${year}${month}${day}`;
}

export function parseToDate(value: string | Date | Ref<Date> | null, delimiter = '/'): Date {
  if (typeof value === 'string') {
    const valueArr = formatToString(value, '/', delimiter).split('/');
    const year = Number(valueArr[0]) + 1911;
    const month = Number(valueArr[1]);
    const day = Number(valueArr[2]);
    return new Date(year, month - 1, day);
  } else {
    return unref(value);
  }
}

//把日期用民國XXX年XX月XX日顯示
export function formatDateInChinese(value: Ref<Date> | Date | null): string {
  if (value === null) return '(未填)';
  const date: Date | null = unwrap(value);
  if (date) {
    const year = (date.getFullYear() - 1911).toString().padStart(3, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}年${month}月${day}日`;
  }
  return '';
}

//給start和end計算日期差多少天
export function dateDiffinDays(startDate: Date, endDate: Date): number {
  if (startDate === null || endDate === null) {
    return null;
  }
  const endDateTime = endDate.getTime();
  const startDateTime = startDate.getTime();
  return Math.floor((endDateTime - startDateTime) / (24 * 3600 * 1000));
}

export function formatToYYY(value: Ref<Date> | Date | null): string {
  if (value === null) return '(未填)';
  const date: Date | null = unwrap(value);
  if (date) {
    const year = (date.getFullYear() - 1911).toString().padStart(3, '0');
    return `${year}`;
  }
  return '';
}

export function toJsDate(adDateStr: string): Date | null {
  return adDateStr ? new Date(adDateStr) : null;
}

export function parseStringDate(date, isCh = false): string {
  if (_isDate(date)) return date;
  if (!date) {
    return '';
  } else if (date.replace('/', '').length < 6) {
    let newString: string = `0${date.replace('/', '')}`;
    let year = newString.substring(newString.length - 5, newString.length - 2);
    let month = newString.substring(newString.length - 2, newString.length);
    if (isCh) {
      return `${year}年${month}月`;
    }
    return `${year}/${month}`;
  } else {
    let newString: string = `0${date.replace('/', '')}`;
    let year = newString.substring(newString.length - 7, newString.length - 4);
    let month = newString.substring(newString.length - 4, newString.length - 2);
    let day = newString.substring(newString.length - 2, newString.length);
    if (isCh) {
      return `${year}年${month}月${day}日`;
    }
    return `${year}/${month}/${day}`;
  }
  return '';
}
