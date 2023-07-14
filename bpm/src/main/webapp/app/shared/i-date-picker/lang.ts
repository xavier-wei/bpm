export const LANG = {
  // the locale of formatting and parsing function
  formatLocale: {
    // MM
    months: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
    // MM
    monthsShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
    // d
    weekdays: ['日', '一', '二', '三', '四', '五', '六'],
    // d
    weekdaysShort: ['日', '一', '二', '三', '四', '五', '六'],
    // d
    weekdaysMin: ['日', '一', '二', '三', '四', '五', '六'],
    // first day of week
    firstDayOfWeek: 0,
    // first week contains January 1st.
    firstWeekContainsDate: 1,
    // // format 'a', 'A'
    // meridiem: (h: number, _: number, isLowercase: boolean) => {
    //   const word = h < 12 ? 'AM' : 'PM';
    //   return isLowercase ? word.toLocaleLowerCase() : word;
    // },
    // // parse ampm
    // meridiemParse: /[ap]\.?m?\.?/i,
    // // parse ampm
    // isPM: (input: string) => {
    //   return `${input}`.toLowerCase().charAt(0) === 'p';
    // }
  },
  // // the calendar header, default formatLocale.weekdaysMin
  // days: [],
  // // the calendar months, default formatLocale.monthsShort
  // months: [],
  // // the calendar title of year
  yearFormat: 'YYYY年',
  // // the calendar title of month
  // monthFormat: 'MMM',
  // // the calendar title of month before year
  monthBeforeYear: false,
};
