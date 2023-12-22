import { unref } from 'vue-demi';

export default function (rules, form) {
  let valid = true;

  const formData = unref(form);
  if (formData.isTcp === '' && formData.isUdp === '') return false;

  return valid;
}
