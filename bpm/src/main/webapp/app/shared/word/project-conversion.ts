
export function changeProject(project: any): string {

    let projectList = ''

    if (project.isHrSys === '1') {
        projectList += '人事差勤系統,'
    }
    if (project.isAdSys === '1') {
        projectList += 'AD系統帳號,'
    }
    if (project.isMeetingRoom === '1') {
        projectList += '會議室管理系統,'
    }
    if (project.isOdSys === '1') {
        projectList += '公文系統帳號,'
    }
    if (project.isEmailSys === '1') {
        projectList += '電子郵件帳號,'
    }
    if (project.isPccWww === '1') {
        projectList += '全球資訊網帳號,'
    }
    if (project.isPccHome === '1') {
        projectList += '會內資訊網站帳號,'
    }
    if (project.isPccPis === '1') {
        projectList += '政府電子採購網帳號,'
    }
    if (project.isOtherSys1 === '1') {
        projectList += '其他系統1帳號,'
    }
    if (project.isOtherSys2 === '1') {
        projectList += '其他系統2帳號,'
    }
    if (project.isOtherSys3 === '1') {
        projectList += '其他系統3帳號,'
    }
    return projectList.substring(0, projectList.length - 1);
}


