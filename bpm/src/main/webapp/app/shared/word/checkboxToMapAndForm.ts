//把l410畫面有打勾的checkbox 轉成後端需要的Map跟Form
// import {Object} from 'typescript/lib/lib.es2019.object.d.ts';
export function checkboxToMapAndForm(data: any, form: any, variables: any): any {

  if (data.checkbox === '1' && data.systemApplyName === '人事差勤系統') {
    form.isHrSys = '1'
    form.hrSys = data.sys
    form.hrSysChange = data.sysChange
    form.hrSysAdmUnit = data.admUnit
    form.hrSysStatus = data.admStatus
    form.hrSysEnableDate = data.admEnableDate
    form.hrSysAdmName = data.admName
    let mapData = new Map<string, object>();
    mapData.set('isHrSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData));
  } else if (data.systemApplyName === '人事差勤系統') {
    form.isHrSys = '0'
    form.hrSys = null
    form.hrSysChange = null
    form.hrSysStatus = null
    form.hrSysEnableDate = null
    form.hrSysAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isHrSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === 'AD帳號') {
    form.isAdSys = '1'
    form.adAccount = data.systemApplyInput
    form.adSys = data.sys
    form.adSysChange = data.sysChange
    form.adSysAdmUnit = data.admUnit
    form.adSysStatus = data.admStatus
    form.adSysEnableDate = data.admEnableDate
    form.adSysAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isAdSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === 'AD帳號') {
    form.isAdSys = '0'
    form.adAccount = null
    form.adSys = null
    form.adSysChange = null
    form.adSysStatus = null
    form.adSysEnableDate = null
    form.adSysAdmName = null

    let mapData = new Map<string, object>();
    mapData.set('isAdSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '會議室管理系統管理權限') {

    form.isMeetingRoom = '1'
    form.meetingRoom = data.sys
    form.meetingRoomChange = data.sysChange
    form.meetingRoomAdmUnit = data.admUnit
    form.meetingRoomStatus = data.admStatus
    form.meetingRoomEnableDate = data.admEnableDate
    form.meetingRoomAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isMeetingRoom', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '會議室管理系統管理權限') {
    form.isMeetingRoom = '0'
    form.meetingRoom = null
    form.meetingRoomChange = null
    form.meetingRoomStatus = null
    form.meetingRoomEnableDate = null
    form.meetingRoomAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isMeetingRoom', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '公文管理系統角色') {

    form.isOdSys = '1'

    if (form.applyItem.length > 0) {
      form.odSysRole = form.applyItem.join(',')
    }

    form.odSys = data.sys
    form.odSysOther = data.sysChange
    form.odSysAdmUnit = data.admUnit
    form.odSysStatus = data.admStatus
    form.odSysEnableDate = data.admEnableDate
    form.odSysAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isOdSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '公文管理系統角色') {
    form.isOdSys = '0'
    form.odSysRole = null;
    form.odSys = null
    form.odSysOther = null
    form.odSysStatus = null
    form.odSysEnableDate = null
    form.odSysAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isOdSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '電子郵件帳號') {

    form.isEmailSys = '1'
    form.emailSysAccount = data.systemApplyInput
    form.emailSys = data.sys
    form.emailApply1 = data.emailApply1
    form.emailApply2 = data.emailApply2
    form.emailSysChange = data.sysChange
    form.emailSysAdmUnit = data.admUnit
    form.emailSysStatus = data.admStatus
    form.emailSysEnableDate = data.admEnableDate
    form.emailSysAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isEmailSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '電子郵件帳號') {
    form.isEmailSys = '0'
    form.emailSysAccount = null
    form.emailSys = null
    form.emailApply1 = null
    form.emailApply2 = null
    form.emailSysChange = null
    form.emailSysStatus = null
    form.emailSysEnableDate = null
    form.emailSysAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isEmailSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  //只有 全球資訊網 要用includes() 去判斷 ， 資料庫完整是 全球資訊網 (https://www.pcc.gov.tw)
  //怕會遇到網址變更或是少了空白，導致isWebSite不會存進map裡面
  if (data.checkbox === '1' && data.systemApplyName.includes('全球資訊網')) {
    form.isWebSite = '1'
    form.isPccWww = '1'
    form.webSite = data.sys
    form.isUnitAdm = data.isUnitAdm
    form.isUnitDataMgr = data.isUnitDataMgr
    form.isWebSiteOther = data.isWebSiteOther
    form.webSiteOther = data.otherRemark
    form.webSiteAdmUnit = data.admUnit
    form.webSiteStatus = data.admStatus
    form.webSiteEnableDate = data.admEnableDate
    form.webSiteAdmName = data.admName
    let mapData = new Map<string, object>();
    mapData.set('isWebSite', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
    // }
  } else if (data.systemApplyName.includes('全球資訊網')) {
    form.isWebSite = '0'
    form.isPccWww = '0'
    form.webSite = null
    form.isUnitAdm = null
    form.isUnitDataMgr = null
    form.isWebSiteOther = null
    form.webSiteOther = null
    form.webSiteStatus = null
    form.webSiteEnableDate = null
    form.webSiteAdmName = null

    let mapData = new Map<string, object>();
    mapData.set('isWebSite', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === '政府電子採購網') {

    form.isPccPis = '1'
    form.pccPisAccount = data.systemApplyInput
    form.pccPis = data.sys
    form.pccPisChange = data.sysChange
    form.pccPisAdmUnit = data.admUnit
    form.pccPisStatus = data.admStatus
    form.pccPisEnableDate = data.admEnableDate
    form.pccPisAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isPccPis', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '政府電子採購網') {
    form.isPccPis = '0'
    form.pccPisAccount = null
    form.pccPis = null
    form.pccPisChange = null
    form.pccPisStatus = null
    form.pccPisEnableDate = null
    form.pccPisAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isPccPis', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === '公共工程案件審議資訊系統') {

    form.isRevSys = '1'
    form.revSysAccount = data.systemApplyInput
    form.revSys = data.sys
    form.revSysChange = data.sysChange
    form.revSysAdmUnit = data.admUnit
    form.revSysStatus = data.admStatus
    form.revSysEnableDate = data.admEnableDate
    form.revSysAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isRevSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '公共工程案件審議資訊系統') {
    form.isRevSys = '0'
    form.revSysAccount = null
    form.revSys = null
    form.revSysChange = null
    form.revSysStatus = null
    form.revSysEnableDate = null
    form.revSysAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isRevSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '災後復建工程經費審議及執行資訊系統') {

    form.isRecSys = '1'
    form.recSysAccount = data.systemApplyInput
    form.recSys = data.sys
    form.recSysChange = data.sysChange
    form.recSysAdmUnit = data.admUnit
    form.recSysStatus = data.admStatus
    form.recSysEnableDate = data.admEnableDate
    form.recSysAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isRecSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '災後復建工程經費審議及執行資訊系統') {
    form.isRecSys = '0'
    form.recSysAccount = null
    form.recSys = null
    form.recSysChange = null
    form.recSysStatus = null
    form.recSysEnableDate = null
    form.recSysAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isRecSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '公共工程標案管理系統') {

    form.isBidSys = '1'
    form.bidSysAccount = data.systemApplyInput
    form.bidSys = data.sys
    form.bidSysChange = data.sysChange
    form.bidSysAdmUnit = data.admUnit
    form.bidSysStatus = data.admStatus
    form.bidSysEnableDate = data.admEnableDate
    form.bidSysAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isBidSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '公共工程標案管理系統') {
    form.isBidSys = '0'
    form.bidSysAccount = null
    form.bidSys = null
    form.bidSysChange = null
    form.bidSysStatus = null
    form.bidSysEnableDate = null
    form.bidSysAdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isBidSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === '本會其他資通系統1') {

    form.isOtherSys1 = '1'
    form.otherSys1ServerName = data.otherSys
    form.otherSys1Account = data.otherSysAccount
    form.otherSys1 = data.sys
    form.otherSys1Change = data.sysChange
    form.otherSys1AdmUnit = data.admUnit
    form.otherSys1Status = data.admStatus
    form.otherSys1EnableDate = data.admEnableDate
    form.otherSys1AdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isOtherSys1', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '本會其他資通系統1') {
    form.isOtherSys1 = '0'
    form.otherSys1ServerName = null
    form.otherSys1Account = null
    form.otherSys1 = null
    form.otherSys1Change = null
    form.otherSys1Status = null
    form.otherSys1EnableDate = null
    form.otherSys1AdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isOtherSys1', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '本會其他資通系統2') {

    form.isOtherSys2 = '1'
    form.otherSys2ServerName = data.otherSys
    form.otherSys2Account = data.otherSysAccount
    form.otherSys2 = data.sys
    form.otherSys2Change = data.sysChange
    form.otherSys2AdmUnit = data.admUnit
    form.otherSys2Status = data.admStatus
    form.otherSys2EnableDate = data.admEnableDate
    form.otherSys2AdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isOtherSys2', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '本會其他資通系統2') {
    form.isOtherSys2 = '0'
    form.otherSys2ServerName = null
    form.otherSys2Account = null
    form.otherSys2 = null
    form.otherSys2Change = null
    form.otherSys2Status = null
    form.otherSys2EnableDate = null
    form.otherSys2AdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isOtherSys2', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === '本會其他資通系統3') {

    form.isOtherSys3 = '1'
    form.otherSys3ServerName = data.otherSys
    form.otherSys3Account = data.otherSysAccount
    form.otherSys3 = data.sys
    form.otherSys3Change = data.sysChange
    form.otherSys3AdmUnit = data.admUnit
    form.otherSys3Status = data.admStatus
    form.otherSys3EnableDate = data.admEnableDate
    form.otherSys3AdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('isOtherSys3', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  } else if (data.systemApplyName === '本會其他資通系統3') {
    form.isOtherSys3 = '0'
    form.otherSys3ServerName = null
    form.otherSys3Account = null
    form.otherSys3 = null
    form.otherSys3Change = null
    form.otherSys3Status = null
    form.otherSys3EnableDate = null
    form.otherSys3AdmName = null
    let mapData = new Map<string, object>();
    mapData.set('isOtherSys3', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  return variables;
}


