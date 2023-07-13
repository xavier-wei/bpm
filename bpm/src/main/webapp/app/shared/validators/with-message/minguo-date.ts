import { minguoDate } from '@/shared/validators/raw/minguo-date';

export default {
  $validator: minguoDate,
  $message: '請輸入有效的日期 (格式為 YYYMMDD)',
};
