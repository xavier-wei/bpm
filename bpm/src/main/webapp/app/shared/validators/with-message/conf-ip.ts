import confIp from "@/shared/validators/raw/conf-ip";

export default {
  $validator: confIp,
  $message: '請輸入正確IP，如 111.111.111.111, 0到255之間 ，'+ '<br>' +'可輸入多筆用逗號分隔 , 如 111.111.111.111,222.222.222.222'
};
