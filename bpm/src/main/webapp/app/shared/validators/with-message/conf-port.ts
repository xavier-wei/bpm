import confPort from "@/shared/validators/raw/conf-port";

export default {
  $validator: confPort,
  $message: '請輸入正確PORT (1 ~ 65535 之間)，'+ '<br>' +'可輸入多筆用逗號分隔 , 如 1111,2222'
};
