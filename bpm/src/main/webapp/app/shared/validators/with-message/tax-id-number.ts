import taxIdNumber from '@/shared/validators/raw/tax-id-number';

export default {
  $validator: taxIdNumber,
  $message: '請輸入正確的統一編號',
};
