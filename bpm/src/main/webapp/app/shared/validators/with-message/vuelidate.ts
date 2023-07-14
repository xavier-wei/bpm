import {
  helpers,
  alpha as vuelidateAlpha,
  alphaNum as vuelidateAlphaNum,
  and as vuelidateAnd,
  between as vuelidateBetween,
  decimal as vuelidateDecimal,
  email as vuelidateEmail,
  integer as vuelidateInteger,
  ipAddress as vuelidateIpAddress,
  macAddress as vuelidateMacAddress,
  maxLength as vuelidateMaxLength,
  maxValue as vuelidateMaxValue,
  minLength as vuelidateMinLength,
  minValue as vuelidateMinValue,
  not as vuelidateNot,
  numeric as vuelidateNumeric,
  or as vuelidateOr,
  required as vuelidateRequired,
  requiredIf as vuelidateRequiredIf,
  requiredUnless as vuelidateRequiredUnless,
  sameAs as vuelidateSameAs,
  url as vuelidateUrl,
} from '@vuelidate/validators';
import { ValidationRule } from '@vuelidate/core';
import { Ref, unref } from 'vue-demi';

export const alpha = helpers.withMessage('請輸入英文字', vuelidateAlpha);
export const alphaNum = helpers.withMessage('請輸入英數字', vuelidateAlphaNum);
export const and = (validators: ValidationRule[], errorMessage: string = '輸入的值不符合所有的檢核規則') => helpers.withMessage(errorMessage, vuelidateAnd(...validators));
export const between = (min: number | Ref<number>, max: number | Ref<number>, errorMessage?: string) =>
  helpers.withMessage(errorMessage ? errorMessage : `輸入的值必須介於 ${unref(min)} 與 ${unref(max)} 之間`, vuelidateBetween(min, max));
export const decimal = helpers.withMessage('請輸入數字', vuelidateDecimal);
export const email = helpers.withMessage('輸入的值不是合法的 E-Mail', vuelidateEmail);
export const emailWithErrMsg = (errorMessage: string = '輸入的值不是合法的 E-Mail') => helpers.withMessage(errorMessage, vuelidateEmail);
export const integer = helpers.withMessage('請輸入整數', vuelidateInteger);
export const ipAddress = helpers.withMessage('輸入的值不是合法的 IP Address', vuelidateIpAddress);
export const macAddress = (separator: string | Ref<string>, errorMessage: string = '輸入的值不是合法的 MAC Address') =>
  helpers.withMessage(errorMessage, vuelidateMacAddress(separator));
export const maxLength = (max: number | Ref<number>, errorMessage?: string) =>
  helpers.withMessage(errorMessage ? errorMessage : `輸入的長度超過 ${unref(max)}`, vuelidateMaxLength(max));
export const maxValue = (max: number | Ref<number> | string | Ref<string>, errorMessage?: string) =>
  helpers.withMessage(errorMessage ? errorMessage : `輸入的值不能大於 ${unref(max)}`, vuelidateMaxValue(max));
export const minLength = (min: number | Ref<number>, errorMessage?: string) => helpers.withMessage(errorMessage ? errorMessage : `輸入的長度不足 ${unref(min)}`, vuelidateMinLength(min));
export const minValue = (min: number | Ref<number> | string | Ref<string>, errorMessage?: string) =>
  helpers.withMessage(errorMessage ? errorMessage : `輸入的值不能小於 ${unref(min)}`, vuelidateMinValue(min));
export const not = (validator: ValidationRule, errorMessage: string = '輸入的值不符合檢核規則') => helpers.withMessage(errorMessage, vuelidateNot(validator));
export const numeric = helpers.withMessage('請輸入正數', vuelidateNumeric);
export const or = (validator: ValidationRule, errorMessage: string = '輸入的值不符合部份的檢核規則') => helpers.withMessage(errorMessage, vuelidateOr(validator));
export const required = helpers.withMessage('請輸入值', vuelidateRequired);
export const requiredWithErrMsg = (errorMessage: string = '請輸入值') => helpers.withMessage(errorMessage, vuelidateRequired);
export const requiredIf = (prop: boolean | string | (() => boolean | Promise<boolean>), errorMessage: string = '請輸入值') =>
  helpers.withMessage(errorMessage, vuelidateRequiredIf(prop));
export const requiredUnless = (prop: boolean | string | (() => boolean | Promise<boolean>), errorMessage: string = '請輸入值') =>
  helpers.withMessage(errorMessage, vuelidateRequiredUnless(prop));
export const sameAs = <E = unknown>(equalTo: E, otherName?: string, errorMessage?: string) =>
  helpers.withMessage(errorMessage ? errorMessage : `值必須與 ${otherName} 的值相同`, vuelidateSameAs(equalTo));
export const url = helpers.withMessage('輸入的值不是合法的網址', vuelidateUrl);
