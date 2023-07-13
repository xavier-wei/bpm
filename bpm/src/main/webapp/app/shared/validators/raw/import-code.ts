import { helpers } from '@vuelidate/validators';

export default function importCode(importCode: string): boolean {
  if (!helpers.req(importCode)) {
    return true;
  }

  if (!/^[A-Z]{1}[0-9]{1}$/.test(importCode)) {
    return false;
  } else {
    return true;
  }
}
