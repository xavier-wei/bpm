import bidPrjno from '@/shared/validators/raw/bid-prjno';

export default {
  $validator: bidPrjno,
  $message: "請輸入英數字及'-'(連字號)",
};
