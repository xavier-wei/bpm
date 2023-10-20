import {forEach as _forEach,} from 'lodash';

//用來控制pending.vue頁面裡面 下屬查詢按鈕是否顯示
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

