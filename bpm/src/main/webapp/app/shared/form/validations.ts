import { computed, watch, WatchStopHandle,Ref } from '@vue/composition-api';
import { useGetters, useStore } from '@u3u/vue-hooks';
import useVuelidate from '@vuelidate/core';
import mitt, { Emitter } from 'mitt';
import { forEach as _forEach, min as _min, size as _size } from 'lodash';
import $ from 'jquery';

/**
 * 驗證群組。
 *
 * 使用 `string[]` 來表示一個群組，成員之間的驗證狀態會互相連動。如果有多個群組請使用 `string[][]` 指定。
 */
export type ValidationGroups = string[] | string[][] | null;

/**
 * 將表單 Reactive 物件與驗挸證規則及其他表單基本操作進行包裝。
 *
 * @param rules 驗證規則。
 * @param form 表單物件。
 * @param formDefaults 表單物件初始值。
 * @param groups 驗證群組，群組裡的任何一個欄位異動都會觸發同群組內其他欄位的驗證，可以用在日期起迄日兩個欄位的連動驗證。
 * @param options 其他設定值。
 */
export function useValidation<T>(
  rules,
  form: T | Ref<T>,
  formDefaults: T,
  groups?: ValidationGroups,
  options = {
    $autoDirty: true,
    $lazy: true,
  }
) {
  const $v = useVuelidate(rules, form, options);
  const emitter: Emitter = mitt();
  const watchStopHandles: WatchStopHandle[] = [];
  const errorCollector = computed(() => useGetters(['errorCollector']).errorCollector.value);

  // 監聽各個群組驗證
  if (groups && groups.length > 1) {
    watchStopHandles.splice(0, watchStopHandles.length, ...watchGroups($v, form, groups));
  }

  /**
   * 重設表單及驗證狀態。
   */
  const reset = () => {
    // for (const [key, value] of Object.entries(formDefaults)) {
    //   form[key] = value;
    // }

    // FIXME $reset 直接叫的話， select 欄位還會是 $dirty 的，不知道為什麼
    // 目前這樣子髒改後行為是正常了，但是會看到 invalid feedback 跑出來見客一下...
    // setTimeout($v.value.$reset);

    new Promise<void>(resolve => {
      // 如有群組驗證時，為避免reset後觸發監聽，又將欄位$touch成$dirty，所以先停止所有監聽，reset完再重新監聽。
      if (watchStopHandles.length > 0) watchStopHandles.forEach(watchStopHandle => watchStopHandle());

      Object.entries(formDefaults).forEach(([key, value]) => (form[key] = value));
      // function resetToDefault(defaultObj: any, obj: any) {
      //   Object.entries(defaultObj).forEach( ([key, value]) => {
      //     typeof value === 'object' && !!value ? resetToDefault(value, obj[key]) : obj[key] = value; // obj可能是undefined會報錯
      //   })
      // }
      // resetToDefault(formDefaults, form);
      resolve();
    }).then(() => {
      $v.value.$reset();
      if (groups && groups.length > 1) watchStopHandles.splice(0, watchStopHandles.length, ...watchGroups($v, form, groups));
    });
  };

  function moveToTopErrorField() {
    if (_size(errorCollector.value) === 0) return;
    let top = null;
    _forEach(errorCollector.value, v => {
      const eleTop = $(v).offset().top;
      if (top === null) top = eleTop;
      else if (top > eleTop) top = eleTop;
    });
    top -= 100;
    if (top < 0) top = 0;
    $($('.is-invalid')[0]).focus();
    $('html, body').animate({ scrollTop: top.toString() + 'px' });
  }

  /**
   * 確認表單是否符合驗證規則。
   */
  async function checkValidity(): Promise<boolean> {
    const valid = await $v.value.$validate();
    if (!valid) moveToTopErrorField();
    else useStore().value.commit('setErrorCollector', []);
    return valid;
  }

  // const watches = [];
  // if (groups && groups.length > 1) {
  //   if (Array.isArray(groups[0])) {
  //     watches.push(...groups);
  //   } else {
  //     watches.push([...groups]);
  //   }
  //   const watchComputed = watches.map(group => group.map(field => computed(() => form[field])));
  //   watchGroups($v, form, watchComputed, watches, emitter);

  //   emitter.on('crossFiledChange', event => {
  //     event.group.forEach(field => {
  //       // $v.value[field].$reset();
  //       // $v.value[field].$touch();
  //     });
  //   });
  // }

  return { $v, checkValidity, reset };
}

function watchGroups($v, form, groups): WatchStopHandle[] {
  const watchStopHandles: WatchStopHandle[] = [];
  if (!Array.isArray(groups[0])) {
    groups = [groups];
  }
  const computedGroups = groups.map(group => group.map(field => computed(() => form[field])));
  computedGroups.forEach((computedGroup, index) => {
    watchStopHandles.push(
      watch(computedGroup, () => {
        groups[index].forEach(field => {
          new Promise<void>(resolve => {
            $v.value[field].$reset();
            resolve();
          }).then(() => {
            $v.value[field].$touch();
          });
        });
      })
    );
  });
  return watchStopHandles;
}

// function watchGroups($v, form, watchGroups: string[][], groups: string[][], emitter: Emitter) {
//   let isChange = false;
//   watchGroups.forEach((group, index) => {
//     watch(group, (value, oldValue) => {
//       for (let i = 0; i < value.length; i++) {
//         if (value[i] != oldValue[i]) {
//           isChange = true;
//           break;
//         }
//       }
//       emitter.emit('crossFiledChange', { group: groups[index] });
//     });
//   });
// }

/**
 * 判斷驗證狀態，搭配 BootstrapVue 的 state mixin 使用。
 *
 * @param v 要驗證的欄位物件。
 * @returns 驗證狀態，欄位值還沒異動前回傳 `null`;
 * 異動後如果驗證失敗則回傳 `false`，驗證通過回傳 `true`。
 */
export function validateState(v): boolean | null {
  return v.$dirty ? !v.$error : null;
}
