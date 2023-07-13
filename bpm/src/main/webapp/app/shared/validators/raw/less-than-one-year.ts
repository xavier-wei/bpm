import { unref } from 'vue-demi';

export default function (givenValue: any) {
  return (value: any) => {
    const compareValue = unref(value);
    const lessValue = unref(givenValue);
    const startDate = new Date(dateStringFormat(lessValue, '00:00:00'));
    const endDate = new Date(dateStringFormat(compareValue, '23:59:59'));
    if (compareValue && lessValue) {
      const months = (endDate.getFullYear() - startDate.getFullYear()) * 12 + (endDate.getMonth() - startDate.getMonth());
      return months <= 12;
    }
    return true;
  };
}

const dateStringFormat = (date: string, time: string) => {
  const year = date.substring(0, 3);
  const month = date.substring(3, 5);
  const day = date.substring(5, 7);
  return year + '/' + month + '/' + day + ' ' + time;
};
