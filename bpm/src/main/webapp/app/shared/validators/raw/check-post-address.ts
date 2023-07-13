import { unref } from 'vue-demi';
import { AdmPostAddr } from '@/shared/model/adm/adm-post-addr.model';
/**
 * @param {*} givenValue
 * @param {*} condition
 * @return {function(*=): boolean}
 */
//givenValue=> T 可以為空
export default function (givenValue) {
  return (value: AdmPostAddr) => {
    console.log('!!!!!!!!', value);
    if (givenValue) {
      return (
        (value.areaCode === '' && value.cityCode === '' && value.zip === '' && value.address === '') ||
        (value.areaCode !== '' && value.cityCode !== '' && value.zip !== '' && value.address !== '')
      );
    } else {
      return value.areaCode !== '' && value.cityCode !== '' && value.zip !== '' && value.address !== '';
    }

    // const targetValue = unref(value);
    // const sourceValue = unref(givenValue);
    // console.log('sourceValue.call()', sourceValue.call());
    // if (sourceValue.call() !== '') {
    //   return targetValue !== '' && targetValue !== null && targetValue !== undefined;
    // } else {
    //   console.log('else');
    //   return targetValue === '' || targetValue === null || targetValue === undefined;
    // }
    // return new Promise((resolve, reject) => {
    //   setTimeout(() => {
    //     if (sourceValue.call() !== '') {
    //       console.log('sourceValue === ""');
    //       console.log('sourceValue.call()', sourceValue.call().value);
    //       resolve(targetValue !== '' && targetValue !== null && targetValue !== undefined);
    //     } else {
    //       console.log('else');
    //       resolve(targetValue === '' || targetValue === null || targetValue === undefined);
    //     }
    //   }, 100);
    // });
  };
}
