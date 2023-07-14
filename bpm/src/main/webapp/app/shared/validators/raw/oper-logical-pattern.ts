import { unref } from 'vue-demi';

/**
 * @param {*} givenValue
 * @return {function(*=): boolean}
 */
export default function operLogicalPattern(givenValue): () => boolean {
  return () => {
    const patternResult = unref(givenValue);
    return !patternResult;
  };
}
