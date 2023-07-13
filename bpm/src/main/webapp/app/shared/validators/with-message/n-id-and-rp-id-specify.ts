import nIdAndRpIdSpecify from '@/shared/validators/raw/n-id-and-rp-id-specify';

export default {
  $validator: nIdAndRpIdSpecify,
  $message: '請輸入正確的身分證號或居留證號',
};
