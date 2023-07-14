import { Ref, unref } from "@vue/composition-api";
import dayjs from "dayjs";

export default function (compareValue: number | string | Date | Ref<number> | Ref<string> | Ref<Date>) {
  return (value: number | string | Date) => {
    const compareVal = unref(compareValue);
    if (compareVal instanceof Date) {
      return !value || !compareVal || (dayjs(value).isBefore(compareVal) || dayjs(value).isSame(compareVal));
    }
    return !value || !compareVal || Number(value) <= Number(compareVal);
  }
}