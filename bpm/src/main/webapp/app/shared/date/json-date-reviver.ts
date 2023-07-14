import dayjs from 'dayjs';

const dateFormat = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(\.\d{3})?Z$/;

export function dateReviver(key, value) {
  if (typeof value === 'string') {
    if (dateFormat.test(value)) {
      return dayjs(value).toDate();
    }
  }
  return value;
}
