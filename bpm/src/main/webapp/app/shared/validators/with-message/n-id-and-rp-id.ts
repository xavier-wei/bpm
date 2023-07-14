import nIdAndRpId from '@/shared/validators/raw/n-id-and-rp-id';

export default {
  $validator: nIdAndRpId,
  $message: '請輸入正確的身分證號或居留證號',
};
