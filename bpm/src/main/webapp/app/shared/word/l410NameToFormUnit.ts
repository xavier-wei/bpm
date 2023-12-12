/**
 * 根據提供的系統名稱，將對應的管理單位初始化設定到表單物件中。
 * @param name - 系統名稱。
 * @param form - 要更新的表單物件。
 * @param init - 單位代號。
 * @returns 更新後的表單物件，如果系統名稱無法匹配任何預期的值，則返回 null。
 */
export function l410NameToFormUnit(name: any, form: any, init: any): any {

  if (name === '人事差勤系統') {
    form.hrSysAdmUnit = init;
    return form;
  } else if (name === 'AD帳號') {
    form.adSysAdmUnit = init;
    return form;
  } else if (name === '會議室管理系統管理權限') {
    form.meetingRoomAdmUnit = init;
    return form;
  } else if (name === '公文管理系統角色') {
    form.emailSysAdmUnit = init;
    return form;
  } else if (name === '電子郵件帳號') {
    form.adSysAdmUnit = init;
    return form;
  } else if (name === '全球資訊網&會內資訊網') {
    form.webSiteAdmUnit = init;
    return form;
  } else if (name === '政府電子採購網') {
    form.pccPisAdmUnit = init;
    return form;
  } else if (name === '公共工程案件審議資訊系統') {
    form.revSysAdmUnit = init;
    return form;
  } else if (name === '災後復建工程經費審議及執行資訊系統') {
    form.recSysAdmUnit = init;
    return form;
  } else if (name === '公共工程標案管理系統') {
    form.bidSysAdmUnit = init;
    return form;
  } else if (name === '本會其他資通系統1') {
    form.otherSys1AdmUnit = init;
    return form;
  } else if (name === '本會其他資通系統2') {
    form.otherSys2AdmUnit = init;
    return form;
  } else if (name === '本會其他資通系統3') {
    form.otherSys3AdmUnit = init;
    return form;
  } else {
    return null;
  }

}
