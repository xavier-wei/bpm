import {changeCodeNoToCh} from "@/shared/word/directions";
import {formatToString, newformatDate} from "@/shared/date/minguo-calendar-utils";


/**
 * 目前處理單位的轉換元件
 * 根據提供的用戶數據、部門列表和用戶所有數據，找到共同處理的部門信息，並返回特定格式的字符串。
 * @param {any} user - 後端組出來的目前處理人員編，可以是多人 [1234,5678]
 * @param {any} unitList - 部門列表。
 * @param {any} userAllData - 所有 ACNT_IS_VALID=Y 的使用者
 * @returns {string} 返回特定格式的字符串，用於前端顯示。 單人: 王Ｏ明(秘書處文書科) 多人: 王Ｏ明(資訊推動小組)、王Ｏ衛(資訊推動小組)
 */
export function currentProcessingUnit(user: any, unitList: any, userAllData: any) {

  if (user == null) return '';

  //把後端傳來的user轉成 { userId: xxx }
  let userList = user.split(',').map(number => ({userId: number}));

  const commonObjects = findCommonObjects(userAllData, userList);

  let pendingUserId = '';
  let delimiter = '';

  //如果commonObjects兩個以上就用分隔福
  if (commonObjects.length > 1) {
    delimiter = '、'
  }

  //組合出前端顯示參數
  commonObjects.forEach(data => {
    pendingUserId += data.userName + '(' + changeCodeNoToCh(data.deptId, unitList) + ')' + delimiter
  });

  //如果commonObjects兩個以上就把最後一個標點符號去掉
  if (commonObjects.length > 1) {
    pendingUserId = pendingUserId.substring(0, pendingUserId.length - 1);
  }

  return pendingUserId;
}

/**
 * 用user那張表的userId 跟傳進來的user做比較，找出重複的users 物件
 * 根據提供的兩個數組，找到它們共同的對象。
 * @param {any} arr1 - 數組1。
 * @param {any} arr2 - 數組2。
 * @returns {any} 返回共同的對象。
 */
function findCommonObjects(arr1, arr2) {
  return arr1.filter(item1 => arr2.some(item2 => item1.userId === item2.userId));
}

/**
 * 申請事由轉換元件
 *
 * <br> 為了讓iTable內的參數能夠換行
 *
 * @param data 表單資訊
 * @returns  {string}  會依照傳進來的表單資訊組合出特定的reason字串
 */
export function applicationReasonUnit(data) {

  let reason = '';

  if (data.appReason === '1') {
    reason += '新進 , ' + '<br>';
  } else if (data.appReason === '2') {
    reason += '離職 , ' + '<br>';
  } else if (data.appReason === '3') {
    reason += '職務異動 , ' + '<br>';
  }
  if (data.isEnableDate === '1') {
    reason += '生效日期： ' + formatToString(new Date(data.enableDate), '/') + '<br>';
  }
  if (data.isOther === '1') {
    reason += '其他理由： ' + data.otherReason
  }
  return reason;
}

/**
 * 申請人+員編元件
 *
 * <br> 為了讓iTable內的參數能夠換行
 *
 * @param data 表單資訊
 * @returns  {string} 會依照傳進來的表單資訊組合出特定的appName字串
 */
export function appNameUnit(data) {

  let appName = '';

  if (!!data.appName) {
    appName += data.appName
  }

  if (!!data.appEmpid) {
    appName += '<br>' + '(' + data.appEmpid + ')';
  }

  return appName;
}

/**
 * 填表人+員編元件
 *
 * <br> 為了讓iTable內的參數能夠換行
 *
 * @param data 表單資訊
 * @returns  {string} 會依照傳進來的表單資訊組合出特定的filName字串
 */
export function filNameUnit(data) {

  let filName = '';
  if (!!data.filName) {
    filName += data.filName
  }

  if (!!data.filEmpid) {
    filName += '<br>' + '(' + data.filEmpid + ')';
  }

  return filName;
}

/**
 * 需求說明轉換元件
 *
 * <br> 為了讓iTable內的參數能夠換行
 *
 * @param data 表單資訊
 * @returns  {string} 會依照傳進來的表單資訊組合出特定的needNarrative字串
 */
export function needNarrativeUnit(data) {

  let needNarrative = '';

  if (!!data.sourceIp) {

    needNarrative += '來源IP： ' + data.sourceIp + ',' + '<br>';
  }
  if (!!data.targetIp) {

    needNarrative += '目的IP： ' + data.targetIp + ',' + '<br>';
  }
  if (!!data.port) {
    needNarrative += '使用協定(Port)： ' + data.port + ',' + '<br>';
  }

  let tcpAndUdp = '';

  if (data.isTcp === 'Y') {
    tcpAndUdp += 'TCP、'
  }
  if (data.isUdp === 'Y') {
    tcpAndUdp += 'UDP、'
  }

  if (tcpAndUdp !== '') {
    needNarrative += '傳輸模式： ' + tcpAndUdp.substring(0, tcpAndUdp.length - 1) + ',' + '<br>';
  }

  if (!!data.instructions) {
    needNarrative += '用途說明： ' + data.instructions + ',' + '<br>';
  }

  //去除最後面的逗點跟<br>
  return needNarrative.substring(0, needNarrative.length - 5);
}



