export function mapToCheckbox(data: any, form: any): any {

  //所有單位下拉選都直接從BPM_L410_APPLY_MANAGE裡面給admUnit

  if (data.systemApplyName === '人事差勤系統') {
    data.checkbox = form.isHrSys
    data.sys = form.hrSys
    data.sysChange = form.hrSysChange
    data.admStatus = form.hrSysStatus
    data.admEnableDate = form.hrSysEnableDate != null ? new Date(form.hrSysEnableDate) : null
    data.admName = form.hrSysAdmName
  }

  if (data.systemApplyName === 'AD帳號') {
    data.checkbox = form.isAdSys
    data.systemApplyInput = form.adAccount
    data.sys = form.adSys
    data.sysChange = form.adSysChange
    data.admStatus = form.adSysStatus
    data.admEnableDate = form.adSysEnableDate != null ? new Date(form.adSysEnableDate) : null
    data.admName = form.adSysAdmName
  }
  if (data.systemApplyName === '會議室管理系統') {

    data.checkbox = form.isMeetingRoom
    data.sys = form.meetingRoom
    data.sysChange = form.meetingRoomChange
    data.admStatus = form.meetingRoomStatus
    data.admEnableDate = form.meetingRoomEnableDate != null ? new Date(form.meetingRoomEnableDate) : null
    data.admName = form.meetingRoomAdmName
  }

  if (data.systemApplyName === '公文管理系統角色') {
    if (form.odSysRole != null) {
      form.applyItem = form.odSysRole.split(',')
    }
    data.checkbox = form.isOdSys
    data.sys = form.odSys
    data.sysChange = form.odSysOther
    data.admStatus = form.odSysStatus
    data.admEnableDate = form.odSysEnableDate != null ? new Date(form.odSysEnableDate) : null
    data.admName = form.odSysAdmName
  }

  if (data.systemApplyName === '電子郵件帳號') {

    data.checkbox = form.isEmailSys
    data.systemApplyInput = form.emailSysAccount
    data.sys = form.emailSys
    data.emailApply1 = form.emailApply1
    data.emailApply2 = form.emailApply2
    data.sysChange = form.emailSysChange
    data.admStatus = form.emailSysStatus
    data.admEnableDate = form.emailSysEnableDate != null ? new Date(form.emailSysEnableDate) : null
    data.admName = form.emailSysAdmName
  }

  if (data.systemApplyName === '網站帳號') {

    if (form.isWebSite === '1') {
      data.checkbox = form.isWebSite

      let webSiteList = [];

      if (form.isPccWww === '1') {
        webSiteList.push('0')
      }
      if (form.isPccHome === '1') {
        webSiteList.push('1')
      }

      form.webSiteList = webSiteList
      data.sys = form.webSite;
      data.isUnitAdm = form.isUnitAdm
      data.isUnitDataMgr = form.isUnitDataMgr
      data.isWebSiteOther = form.isWebSiteOther
      data.otherRemark = form.webSiteOther
      data.admStatus = form.webSiteStatus
      data.admEnableDate = form.webSiteEnableDate != null ? new Date(form.webSiteEnableDate) : null
      data.admName = form.webSiteAdmName

    }
  }

  if (data.systemApplyName === '政府電子採購網') {

    data.checkbox = form.isPccPis
    data.systemApplyInput = form.pccPisAccount
    data.sys = form.pccPis
    data.sysChange = form.pccPisChange
    data.admStatus = form.pccPisStatus
    data.admEnableDate = form.pccPisEnableDate != null ? new Date(form.pccPisEnableDate) : null
    data.admName = form.pccPisAdmName
  }


  if (data.systemApplyName === '技師與工程技術顧問公司管理資訊系統') {

    data.checkbox = form.isEngAndPrjInfoSys
    data.systemApplyInput = form.engAndPrjInfoSysAccount
    data.sys = form.engAndPrjInfoSys
    data.sysChange = form.engAndPrjInfoSysChange
    data.admStatus = form.engAndPrjInfoSysStatus
    data.admEnableDate = form.engAndPrjInfoSysEnableDate != null ? new Date(form.engAndPrjInfoSysEnableDate) : null
    data.admName = form.engAndPrjInfoSysAdmName
  }

  if (data.systemApplyName === '公共工程案件審議資訊系統') {

    data.checkbox = form.isRevSys
    data.systemApplyInput = form.revSysAccount
    data.sys = form.revSys
    data.sysChange = form.revSysChange
    data.admStatus = form.revSysStatus
    data.admEnableDate = form.revSysEnableDate != null ? new Date(form.revSysEnableDate) : null
    data.admName = form.revSysAdmName
  }

  if (data.systemApplyName === '災後復建工程經費審議及執行資訊系統') {

    data.checkbox = form.isRecSys
    data.systemApplyInput = form.recSysAccount
    data.sys = form.recSys
    data.sysChange = form.recSysChange
    data.admStatus = form.recSysAdmUnit
    data.admEnableDate = form.recSysEnableDate != null ? new Date(form.recSysEnableDate) : null
    data.admName = form.recSysAdmName
  }

  if (data.systemApplyName === '公共工程標案管理系統') {

    data.checkbox = form.isBidSys
    data.systemApplyInput = form.bidSysAccount
    data.sys = form.bidSys
    data.sysChange = form.bidSysChange
    data.admStatus = form.bidSysStatus
    data.admEnableDate = form.bidSysEnableDate != null ? new Date(form.bidSysEnableDate) : null
    data.admName = form.bidSysAdmName
  }

  if (data.systemApplyName === '其他系統1') {

    data.checkbox = form.isOtherSys1
    data.otherSys = form.otherSys1ServerName
    data.otherSysAccount = form.otherSys1Account
    data.sys = form.otherSys1
    data.sysChange = form.otherSys1Change
    data.admStatus = form.otherSys1Status
    data.admEnableDate = form.otherSys1EnableDate != null ? new Date(form.otherSys1EnableDate) : null
    data.admName = form.otherSys1AdmName

  }

  if (data.systemApplyName === '其他系統2') {

    data.checkbox = form.isOtherSys2
    data.otherSys = form.otherSys2ServerName
    data.otherSysAccount = form.otherSys2Account
    data.sys = form.otherSys2
    data.sysChange = form.otherSys2Change
    data.admStatus = form.otherSys2Status
    data.admEnableDate = form.otherSys2EnableDate != null ? new Date(form.otherSys2EnableDate) : null
    data.admName = form.otherSys2AdmName
  }

  if (data.systemApplyName === '其他系統3') {

    data.checkbox = form.isOtherSys3
    data.otherSys = form.otherSys3ServerName
    data.otherSysAccount = form.otherSys3Account
    data.sys = form.otherSys3
    data.sysChange = form.otherSys3Change
    data.admStatus = form.otherSys3Status
    data.admEnableDate = form.otherSys3EnableDate != null ? new Date(form.otherSys3EnableDate) : null
    data.admName = form.otherSys3AdmName

  }


  return data;
}


