import fax from '@/shared/validators/raw/fax';

export default {
  $validator: fax,
  $message: '請確認輸入內容為數字或特殊符號',
};
