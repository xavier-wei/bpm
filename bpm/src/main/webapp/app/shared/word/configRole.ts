import {forEach as _forEach,} from 'lodash';


//1.判斷userData.userRole，有下列權限就去更新資料
//2.判斷使否是下列權限，切換disabled
//判斷是否是資推的人
export function configRoleToBpmIpt(role: any): any {
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

//1.判斷userData.userRole，有下列權限就去更新資料
//2.判斷使否是下列權限，切換disabled
//判斷是否是 機房操作人員
export function configRoleToBpmCrOperator(role: any): any {
  let shouldBreak = false;
  let result = false;
  _forEach(role.split(','), f => {
    if (f === 'BPM_CR_Operator') {
      shouldBreak = true;
      result = true;
    }
    if (shouldBreak) {
      return false;
    }
  })
  return result;
}






