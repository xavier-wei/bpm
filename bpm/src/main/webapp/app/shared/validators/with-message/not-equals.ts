import notEquals from "../raw/not-equals";
import { formatDate } from "@/shared/date/minguo-calendar-utils";

export default function (compareValue: () => any, message?: (value: any) => string) {
  return {
    $validator: notEquals(compareValue),
    $message: () => {
      let value = compareValue();
      if (value instanceof Date) {
        value = formatDate(value, '/');
      }
      return message(value) ? message(value) : `輸入的值不可與${value}相同`;
    },
  };
}