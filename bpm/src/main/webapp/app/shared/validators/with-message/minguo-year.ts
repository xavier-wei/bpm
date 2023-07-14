import { minguoYear } from '@/shared/validators/raw/minguo-year';

export default {
  $validator: minguoYear,
  $message: '請輸入有效的民國年 (格式為 YYY)',
};
