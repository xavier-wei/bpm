import {forEach as _forEach,} from 'lodash';

/**
 * 用來控制pending.vue頁面裡面 下屬查詢按鈕是否顯示
 *
 * 未來如果要開放其他職稱查下屬，就在superior內加入該職稱
 *
 * 根據提供的職稱字符串，檢查是否包含 處長、副處長、主任。
 * @param {any} titleName - 職稱字符串。
 * @returns {boolean} 如果包含 處長、副處長、主任，則返回 true；否則返回 false。
 */
export function superiorFilter(titleName: any): any {
  let shouldBreak = false;
  let result = false;

  const superior = [
    '處長',
    '副處長',
    '主任',
  ];

  _forEach(superior, f => {
    if (f === titleName) {
      shouldBreak = true;
      result = true;
    }
    if (shouldBreak) {
      return false;
    }
  })
  return result;
}

