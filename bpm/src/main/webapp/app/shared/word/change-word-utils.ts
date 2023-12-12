import {newformatDate} from "@/shared/date/minguo-calendar-utils";

/**
 * 根據提供的表單ID、任務名稱、申請日期和顯示選項生成特定格式的主題字符串。
 * @param formId - 表單ID。
 * @param taskName - 任務名稱。
 * @param applyDate - 申請日期。
 * @param show - 顯示選項，如果為 true，則包含任務名稱和申請日期在內。
 * @returns 格式化後的主題字符串。
 */
export function changeSubject(formId: any,taskName: any,applyDate: any, show: any): string {

  if (!formId) return '';

  let formName = formId.substring(0, 4);

  let showTaskName = '';

  if (show) {
    let taskName_ = taskName != null  ? taskName : '';
    let applyDate_ = applyDate != null ? applyDate : '';
    showTaskName = '( ' + formId + ', ' + newformatDate(new Date(applyDate_), '/') + ',' + taskName_ + ')';
  }

  if (formName === 'L410') {
    return formId.substring(0, 4) + '-共用系統使用者帳號申請單' + showTaskName;
  } else if (formName === 'L414') {
    return formId.substring(0, 4) + '-網路服務連結申請單'+ showTaskName;
  }

  return '';
}

