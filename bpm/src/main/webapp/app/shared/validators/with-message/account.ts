import account from '@/shared/validators/raw/account';

export default {
  $validator: account,
  $message: '僅能輸入英文或數字，且至少有一個英文字母。',
};
