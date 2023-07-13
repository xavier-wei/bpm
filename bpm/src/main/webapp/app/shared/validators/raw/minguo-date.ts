import { helpers } from '@vuelidate/validators';
import { unref } from 'vue-demi';
import dayjs from 'dayjs';
import { Ref } from '@vue/composition-api';

export const minguoDate = (value: Ref<string> | string): boolean => {
  value = unref(value);
  if (!helpers.req(value)) {
    return true;
  }
  if (value.length !== 7) {
    return false;
  }
  if (!(!isNaN(Number(value)) && !isNaN(parseInt(value)))) {
    return false;
  }
  // 不接受民前，且沒有民國 0 年這種東西
  if (value[0] === '-' || value.substring(0, 3) === '000') {
    return false;
  }
  const date = (Number(value.substring(0, 3)) + 1911).toString() + value.substring(3);
  return dayjs(date, 'YYYYMMDD', true).isValid();
};
