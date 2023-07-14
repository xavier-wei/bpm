import equalsBooleanOfValue from '@/shared/validators/raw/equals-boolean-of-value';
export default function (compareValue) {
  return {
    $validator: equalsBooleanOfValue(compareValue),
    $message: `請輸入完整地址格式`
  };
}
