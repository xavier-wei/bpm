import nationalId from '@/shared/validators/raw/national-id';

export default {
  $validator: nationalId,
  $message: '請輸入正確的身分證字號',
};
