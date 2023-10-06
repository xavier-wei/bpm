import {changeDealWithUnit} from "@/shared/word/directions";
import {formatToString, newformatDate} from "@/shared/date/minguo-calendar-utils";


//目前處理單位的轉換元件
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
    pendingUserId += data.userName + '(' + changeDealWithUnit(data.deptId, unitList) + ')' + delimiter
  });

  //如果commonObjects兩個以上就把最後一個標點符號去掉
  if (commonObjects.length > 1) {
    pendingUserId = pendingUserId.substring(0, pendingUserId.length - 1);
  }

  return pendingUserId;
}

//用user那張表的userId 跟傳進來的user做比較，找出重複的users 物件
function findCommonObjects(arr1, arr2) {
  return arr1.filter(item1 => arr2.some(item2 => item1.userId === item2.userId));
}

//申請事由轉換元件
export function applicationReasonUnit(data) {

  let reason = '';

  if (data.appReason === '1') {
    reason += '新進 , ' + '\n';
  } else if (data.appReason === '2') {
    reason += '離職 , ' + '\n';
  } else if (data.appReason === '3') {
    reason += '職務異動 , ' + '\n';
  }
  if (data.isEnableDate === '1') {
    reason += '生效日期： ' + formatToString(new Date(data.enableDate), '/') + '\n';
  }
  if (data.isOther === '1') {
    reason += '其他理由： ' + data.otherReason
  }
  return reason;
}

//申請人+員編元件
export function appNameUnit(data) {

  let appName = '';

  if (!!data.appName) {
    appName += data.appName
  }

  if (!!data.appEmpid) {
    appName += '\n' + '(' + data.appEmpid + ')';
  }

  return appName;
}

//填表人+員編元件
export function filNameUnit(data) {

  let filName = '';
  if (!!data.appName) {
    filName += data.filName
  }

  if (!!data.filEmpid) {
    filName += '\n' + '(' + data.filEmpid + ')';
  }

  return filName;
}

//需求說明轉換元件
export function needNarrativeUnit(data) {

  let needNarrative = '';

  if (!!data.sourceIp) {

    needNarrative += '來源IP： ' + data.sourceIp + ',' + '\n';
  }
  if (!!data.targetIp) {

    needNarrative += '目的IP： ' + data.targetIp + ',' + '\n';
  }
  if (!!data.port) {
    needNarrative += '使用協定(Port)： ' + data.port + ',' + '\n';
  }

  let tcpAndUdp = '';

  if (data.isTcp === 'Y') {
    tcpAndUdp += 'TCP、'
  }
  if (data.isUdp === 'Y') {
    tcpAndUdp += 'UDP、'
  }

  if (tcpAndUdp !== '') {
    needNarrative += '傳輸模式： ' + tcpAndUdp.substring(0, tcpAndUdp.length - 1) + ',' + '\n';
  }

  if (!!data.instructions) {
    needNarrative += '用途說明： ' + data.instructions + ',' + '\n';
  }

  let inputText = '';

  //取倒數第二個字元
  const secondToLastIndex = needNarrative.length - 2;

  //去除整個needNarrative倒數第二個 ,
  if (secondToLastIndex >= 0 && needNarrative.charAt(secondToLastIndex) === ',') {
    inputText = needNarrative.slice(0, secondToLastIndex) + needNarrative.slice(secondToLastIndex + 1);
  } else {
    inputText = needNarrative;
  }

  return inputText;
}



