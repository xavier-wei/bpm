import {forEach as _forEach,} from 'lodash';



/**
 * 1.判斷userData.userRole，有下列權限就去更新資料
 * 2.判斷使否是下列權限，切換disabled
 * 判斷是否是資推的人
 * 根據提供的角色字符串，檢查是否包含 BPM_IPT_Operator 或 BPM_IPT_Mgr 角色。
 * @param {any} role - 角色字符串，多個角色用逗號分隔。
 * @returns {boolean} 如果包含 BPM_IPT_Operator 或 BPM_IPT_Mgr 角色，則返回 true；否則返回 false。
 */
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

/**
 * 1.判斷userData.userRole，有下列權限就去更新資料
 * 2.判斷使否是下列權限，切換disabled
 * 判斷是否是機房操作人員
 * 根據提供的角色字符串，檢查是否包含 BPM_CR_Operator角色。
 * @param {any} role - 角色字符串，多個角色用逗號分隔。
 * @returns {boolean} 如果包含 BPM_CR_Operator 角色，則返回 true；否則返回 false。
 */
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


/**
 * 判斷title是否是 科長、處長 用來顯示補件按鈕
 * 根據提供的職稱字符串，檢查是否包含 科長、處長。
 * @param {any} title - 角色字符串，多個角色用逗號分隔。
 * @returns {boolean} 如果包含 科長、處長，則返回 true；否則返回 false。
 */
export function configTitleName(title: any): any {
  let shouldBreak = false;
  let result = false;
  _forEach(title.split(','), f => {
    if (f === '科長' || f === '處長') {
      shouldBreak = true;
      result = true;
    }
    if (shouldBreak) {
      return false;
    }
  })
  return result;
}





