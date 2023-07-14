import { trim as _trim, forEach as _forEach, replace as _replace } from 'lodash';
import $ from 'jquery';

const mingGuoLimitDate = '1911-12-31';

const disabledDateFrom = (date, toDate) => {
  const offset = new Date().getTimezoneOffset();
  date = new Date(date.getTime() - offset * 60 * 1000);
  if (_trim(toDate) === '') return date <= new Date(mingGuoLimitDate);
  return date > toDate || date < new Date(mingGuoLimitDate);
};
const disabledDateTo = (date, fromDate) => {
  const offset = new Date().getTimezoneOffset();
  date = new Date(date.getTime() - offset * 60 * 1000);
  if (_trim(fromDate) === '') return date <= new Date(mingGuoLimitDate);
  else return date < fromDate;
};

const dealWithYearPanel = (elementStr: string, originCValue: Date | string, isFromKey: boolean, isToKey: boolean) => {
  const $yearPanelTd = $(elementStr);
  const isValueEmpty = _trim(originCValue) === '';
  const compareValue = isValueEmpty ? new Date(mingGuoLimitDate) : originCValue;
  const compareYear = new Date(compareValue).getFullYear() - 1911;
  _forEach($yearPanelTd, td => {
    const $td = $(td);
    const tdYear = parseInt($(td)[0].innerText);
    if (isFromKey) tdDisabledAndStopPropagation($td, isValueEmpty ? tdYear < 1 : tdYear < 1 || tdYear > compareYear);
    else if (isToKey) tdDisabledAndStopPropagation($td, isValueEmpty ? tdYear < 1 : tdYear < compareYear);
    else tdDisabledAndStopPropagation($td, tdYear < 1);
  });
};

const dealWithMonthPanel = (
  elementStr: string,
  originCValue: Date | string,
  originPValue: Date | string,
  isFromKey: boolean,
  isToKey: boolean
) => {
  const $monthPanelTd = $(elementStr);
  const isValueEmpty = _trim(originCValue) === '';
  const compareValue = isValueEmpty ? new Date(mingGuoLimitDate) : originCValue;
  const compareDate = new Date(compareValue);
  const compareYear = compareDate.getFullYear() - 1911;
  const compareMonth = compareDate.getMonth();
  const isPanelDateEmpty = _trim(originPValue) === '';
  const panelValue = isPanelDateEmpty ? new Date().toDateString() : new Date(originPValue).toDateString();
  const panelYear = new Date(panelValue).getFullYear() - 1911;
  _forEach($monthPanelTd, td => {
    let onOrOff = false;
    const $td = $(td);
    const tdMonth = parseInt(_replace($td[0].innerText, '月', '')) - 1;
    if (isFromKey) {
      if (isValueEmpty) {
        if (panelYear < 1) onOrOff = true;
        else if (panelYear === compareYear) onOrOff = tdMonth > compareMonth;
      } else {
        if (panelYear < 1 || panelYear > compareYear) onOrOff = true;
        else if (panelYear === compareYear) onOrOff = tdMonth > compareMonth;
      }
      tdDisabledAndStopPropagation($td, onOrOff);
    } else if (isToKey) {
      if (panelYear < compareYear) onOrOff = true;
      else if (panelYear === compareYear) onOrOff = tdMonth < compareMonth;
      tdDisabledAndStopPropagation($td, onOrOff);
    } else tdDisabledAndStopPropagation($td, panelYear < 1);
  });
};

const tdDisabledAndStopPropagation = ($td, onOrOff) => {
  onOrOff ? $td.addClass('disabled') : $td.removeClass('disabled');
  if (onOrOff)
    $td.on('click', event => {
      event.stopPropagation();
    });
  else $td.off('click');
};

export { disabledDateFrom, disabledDateTo, dealWithYearPanel, dealWithMonthPanel };
