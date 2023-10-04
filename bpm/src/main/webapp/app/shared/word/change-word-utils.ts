import {newformatDate} from "@/shared/date/minguo-calendar-utils";


//切換(待處理表單、表單查詢)畫面上[主旨]欄位用
export function changeSubject(subject: any, show: any): string {

  if (!subject) return '';

  let formName = subject.formId.substring(0, 4);

  let showTaskName = '';

  if (show) {
    showTaskName = ',' + subject.taskName
  }

  if (formName === 'L410') {
    return subject.formId.substring(0, 4) + '-共用系統使用者帳號申請單( ' + subject.formId + ', ' + newformatDate(new Date(subject.applyDate), '/') + showTaskName + ')';
  } else if (formName === 'L414') {
    return subject.formId.substring(0, 4) + '-網路服務連結申請單( ' + subject.formId + ', ' + newformatDate(new Date(subject.applyDate), '/') + showTaskName + ')';
  }

  return '';
}


//切換(表單查詢)畫面上[主旨]欄位用
export function changeFormId(formId: any): string {
  if (!formId) return '';

  let formName = formId.substring(0, 4);

  if (formName === 'L410') {
    return formId.substring(0, 4) + '-共用系統使用者帳號申請單';
  } else if (formName === 'L414') {
    return formId.substring(0, 4) + '-網路服務連結申請單';
  }

  return '';
}
