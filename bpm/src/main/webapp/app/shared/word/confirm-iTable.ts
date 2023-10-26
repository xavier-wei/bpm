//判斷審核者需要填寫的欄位，回傳一個array
export function confirmAllData(data: any, userData: any, taskName: any, notificationService: any): any {

  if (userData === data.admUnit && taskName === data.systemApplyName && data.checkbox === '1') {
    if (data.admStatus === '' || data.admEnableDate === null || data.admName === '') {
      if (data.admStatus === '') return notificationService.makeModalComfirmCallback('未填處理情形' + ' : ' + data.systemApplyName, 'danger');
      if (data.admEnableDate === null) return notificationService.makeModalComfirmCallback('未填生效日期' + ' : ' + data.systemApplyName, 'danger');
      if (data.admName === '') return notificationService.makeModalComfirmCallback('未填處理人員' + ' : ' + data.systemApplyName, 'danger');
    }else {
      return true;
    }
  } else {
    return true;
  }
}



