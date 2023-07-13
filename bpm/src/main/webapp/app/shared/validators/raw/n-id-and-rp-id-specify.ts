import { helpers } from '@vuelidate/validators';
import nIdAndRpId from './n-id-and-rp-id';

export default function (id: string): boolean {
  if (!helpers.req(id)) return true;
  id = id.replace(/\s+/g, '');
  if (id === 'Z888888888') return true;
  else return nIdAndRpId(id);
}

