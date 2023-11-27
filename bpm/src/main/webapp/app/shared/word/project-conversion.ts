//l410Query畫面的[系統項目]轉換元件
export function changeProject(project: any): string {

  let projectList = ''

  if (project.isHrSys === '1') {
    projectList += '人事差勤系統,' + '<br>'
  }
  if (project.isAdSys === '1') {
    projectList += 'AD系統帳號,' + '<br>'
  }
  if (project.isMeetingRoom === '1') {
    projectList += '會議室管理系統管理權限,' + '<br>'
  }
  if (project.isOdSys === '1') {
    projectList += '公文系統帳號角色,' + '<br>'
  }
  if (project.isEmailSys === '1') {
    projectList += '電子郵件帳號,' + '<br>'
  }
  if (project.isPccWww === '1') {
    projectList += '全球資訊網帳號,' + '<br>'
  }
  // if (project.isPccHome === '1') {
  //   projectList += '會內資訊網站帳號,' + '<br>'
  // }
  if (project.isPccPis === '1') {
    projectList += '政府電子採購網帳號,' + '<br>'
  }
  // if (project.isEngAndPrjInfoSys === '1') {
  //   projectList += '技師與工程技術顧問公司管理資訊系統,' + '<br>'
  // }
  if (project.isRevSys === '1') {
    projectList += '公共工程案件審議資訊系統,' + '<br>'
  }
  if (project.isRecSys === '1') {
    projectList += '災後復建工程經費審議及執行資訊系統,' + '<br>'
  }
  if (project.isBidSys === '1') {
    projectList += '公共工程標案管理系統,' + '<br>'
  }
  if (project.isOtherSys1 === '1') {
    projectList += '本會其他資通系統1,' + '<br>'
  }
  if (project.isOtherSys2 === '1') {
    projectList += '本會其他資通系統2,' + '<br>'
  }
  if (project.isOtherSys3 === '1') {
    projectList += '本會其他資通系統3,' + '<br>'
  }
  return projectList.substring(0, projectList.length - 5);
}


