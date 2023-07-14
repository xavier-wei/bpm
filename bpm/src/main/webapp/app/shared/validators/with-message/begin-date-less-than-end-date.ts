import beginDateLessThanEndDate from '@/shared/validators/raw/begin-date-less-than-end-date';
import { formatDate } from '@/shared/date/minguo-calendar-utils';

/**
 * Check begin date less than end date.
 * @param { Date } givenValue
 * @returns {NormalizedValidator}
 */
export default function (givenValue) {
  return {
    $validator: beginDateLessThanEndDate(givenValue),
    $message: ({ $params }) => {
      return `結束日期須大於開始日期`;
    },
    $params: { givenValue },
  };
}
