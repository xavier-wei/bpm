import { minguoYearMonth } from '@/shared/validators/raw/mingou-year-month';

export default {
  $validator: minguoYearMonth,
  $message: '請輸入有效的民國年月 (格式為 YYYMM)',
};
