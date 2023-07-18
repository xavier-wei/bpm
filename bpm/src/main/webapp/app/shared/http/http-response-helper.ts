import { AxiosError } from 'axios';
import { handle401Error } from '@/shared/http/handle-401-error';
import NotificationService from '@/shared/notification/notification-service';
import { Problem } from '@/shared/http/problem';
import { ErrorStatus } from './error-status';

const MESSAGE_CODE_TYPE = 'https://www.jhipster.tech/problem/problem-with-code';
const MESSAGE_TYPE = 'https://www.jhipster.tech/problem/problem-with-message';
const CONSTRAINT_VIOLATION_TYPE = 'https://www.jhipster.tech/problem/constraint-violation';

export function notificationErrorHandler(notificationService: NotificationService) {
  return (error: AxiosError | AxiosError[]) => {
    let errors: AxiosError[];
    if (Array.isArray(error)) {
      errors = error;
    } else {
      errors = [];
      errors.push(error);
    }

    errors.forEach(value => {
      if (value.response === null || value.response === undefined) return;
      //判斷axios-inteceptor攔下來的 401 or 403
      handle401Error(value);
      const statusCode = resolveHttpErrorMessage(value);
      const message = statusCode.message;
      if (statusCode.level === 'ERROR') {
        notificationService.danger(message);
      } else if (statusCode.level === 'WARN') {
        notificationService.warning(message);
      } else if (statusCode.level === 'INFO') {
        notificationService.info(message);
      } else if (statusCode.level === 'SUCCESS') {
        notificationService.success(message);
      }
    });
  };
}

export function resolveHttpErrorMessage(error: AxiosError): ErrorStatus {
  let problem: Problem = error.response.data;
  if (error.response) {
    if (error.response.data instanceof ArrayBuffer) {
      const responseString = new TextDecoder('UTF-8').decode(error.response.data);
      problem = JSON.parse(responseString);
    } else {
      problem = error.response.data;
    }
    return resolveMessage(problem);
  }
}

function resolveMessage(problem: Problem): ErrorStatus {
  let message;
  if (problem.type === MESSAGE_CODE_TYPE) {
    return {
      code: problem.title,
      message: problem.detail,
      level: problem.level || 'ERROR',
    };
    //jdl gen出來 預設的回傳型態
  } else if (problem.type === MESSAGE_TYPE) {
    return {
      code: problem.title,
      message: resolveMessageDetail(problem),
      level: 'ERROR',
    };
  } else if (problem.type === CONSTRAINT_VIOLATION_TYPE) {
    // TODO: extract more detail information
    return {
      message: problem.title,
      level: 'ERROR',
    };
  } else {
    if (problem.path) {
      message = `與伺服器 (${problem.path}) 溝通時發生錯誤 (${problem.status})，請稍後再試。如果問題一直無法改善，請洽系統管理人員。`;
    } else {
      message = `與伺服器溝通時發生錯誤 (${problem.status})，請稍後再試。如果問題一直無法改善，請洽系統管理人員。`;
    }
    return {
      message: message,
      level: 'ERROR',
    };
  }
}

function resolveMessageDetail(problem: Problem): string {
  let returnMessage = '';
  switch (problem.status) {
    case 400:
      if (problem.title === 'Bad Request') {
        returnMessage = `與伺服器請求時發生錯誤，請確認輸入內容無誤。如果問題一直無法改善，請洽系統管理人員。`;
      } else {
        returnMessage = problem.title;
        if (problem.detail) {
          returnMessage += '\n' + problem.detail;
        }
      }
      break;
    case 500:
      /**
       * 500預設都是不顯示detail
       * 但如果拋出messageCodeException，訊息會放在detail
       * 所以
       */

      if ('messageCode' === problem.errorKey) {
        returnMessage = problem.detail;
      } else if ('Internal Server Error' === problem.title) {
        returnMessage = `伺服器異常，請稍後再試。如果問題一直無法改善，請洽系統管理人員。`;
      } else {
        returnMessage = `與伺服器溝通時發生錯誤 (${problem.status})，請稍後再試。如果問題一直無法改善，請洽系統管理人員。`;
      }

      break;
    case 401:
      returnMessage = `使用者權限異常，請確認是否有登入`;
      break;
    case 405:
      returnMessage = `伺服器請求或接收路徑錯誤`;
      break;
    default:
      returnMessage = `與伺服器溝通時發生錯誤 (${problem.status})，請稍後再試。如果問題一直無法改善，請洽系統管理人員。`;
      break;
  }
  return returnMessage;
}
