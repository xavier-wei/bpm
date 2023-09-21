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
    mapData.set('hrSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '人事差勤系統'){
    let mapData = new Map<string, object>();
    mapData.set('hrSys', null)
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
    mapData.set('adSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === 'AD帳號'){
    let mapData = new Map<string, object>();
    mapData.set('adSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '會議室管理系統') {

    form.isMeetingRoom = '1'
    form.meetingRoom = data.sys
    form.meetingRoomChange = data.sysChange
    form.meetingRoomAdmUnit = data.admUnit
    form.meetingRoomStatus = data.admStatus
    form.meetingRoomEnableDate = data.admEnableDate
    form.meetingRoomAdmName = data.admName

    let mapData = new Map<string, object>();
    mapData.set('meetingRoom', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '會議室管理系統'){
    let mapData = new Map<string, object>();
    mapData.set('meetingRoom', null)
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
    mapData.set('odSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '公文管理系統角色'){
    let mapData = new Map<string, object>();
    mapData.set('odSys', null)
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
    mapData.set('emailSys', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '電子郵件帳號'){
    let mapData = new Map<string, object>();
    mapData.set('emailSys', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === '網站帳號') {
    if (form.webSiteList.length >= 1) {
      form.webSiteList.forEach(i => {
        if (i === '0') {

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
          mapData.set('pccWww', data)
          let arrData = Array.from(mapData);
          variables.push(Object.fromEntries(arrData))
        } else if (i === '1') {

          form.isWebSite = '1'
          form.isPccHome = '1'
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
          mapData.set('pccHome', data)
          let arrData = Array.from(mapData);
          variables.push(Object.fromEntries(arrData))
        }
      })
    }
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
    mapData.set('pccPis', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '政府電子採購網'){
    let mapData = new Map<string, object>();
    mapData.set('pccPis', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '其他系統1') {

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
    mapData.set('otherSys1', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '其他系統1'){
    let mapData = new Map<string, object>();
    mapData.set('otherSys1', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }


  if (data.checkbox === '1' && data.systemApplyName === '其他系統2') {

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
    mapData.set('otherSys2', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '其他系統2'){
    let mapData = new Map<string, object>();
    mapData.set('otherSys2', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  if (data.checkbox === '1' && data.systemApplyName === '其他系統3') {

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
    mapData.set('otherSys3', data)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }else if(data.systemApplyName === '其他系統3'){
    let mapData = new Map<string, object>();
    mapData.set('otherSys3', null)
    let arrData = Array.from(mapData);
    variables.push(Object.fromEntries(arrData))
  }

  return variables;
}


