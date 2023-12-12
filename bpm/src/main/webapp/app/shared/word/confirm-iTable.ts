
/**
 * 判斷審核者需要填寫的欄位，回傳一個array
 * 根據提供的數據、用戶數據、任務名稱和通知服務，確認是否需要顯示提示信息。
 * @param {any} data - 表單數據。
 * @param {any} userData - 用戶數據。
 * @param {any} taskName - 任務名稱。
 * @param {any} notificationService - 通知服務。
 * @returns {any} 如果需要顯示提示信息，則返回模態確認回調函式；否則返回 true。
 */

export function confirmAllData(data: any, userData: any, taskName: any, notificationService: any): any {

  // 檢查用戶數據、任務名稱和 checkbox 是否符合條件
  if (userData === data.admUnit && taskName === data.systemApplyName && data.checkbox === '1') {
    if (data.admStatus === '' || data.admEnableDate === null || data.admName === '') {
      // 檢查必填項是否有未填寫的情況，並返回相應的提示信息或 true
      if (data.admStatus === '') return notificationService.makeModalComfirmCallback('未填處理情形' + ' : ' + data.systemApplyName, 'danger');
      if (data.admEnableDate === null) return notificationService.makeModalComfirmCallback('未填生效日期' + ' : ' + data.systemApplyName, 'danger');
      if (data.admName === '') return notificationService.makeModalComfirmCallback('未填處理人員' + ' : ' + data.systemApplyName, 'danger');
    }else {
      // 所有必填項都填寫完畢，返回 true
      return true;
    }
  } else {
    // 不需要顯示提示信息，返回 true
    return true;
  }
}



