import {forEach as _forEach,} from 'lodash';

export function configRole(role: any): any {
  let shouldBreak = false;
  let result = false;
  _forEach(role.split(','), f => {
    if (f === 'BPM_IPT_Operator' || f === 'BPM_IPT_Mgr') {
      shouldBreak = true;
      result = true;
    }
    if (shouldBreak) {
      return false;
    }
  })
  return result;
}







