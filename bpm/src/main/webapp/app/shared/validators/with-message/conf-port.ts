import confPort from "@/shared/validators/raw/conf-port";

export default {
  $validator: confPort,
  $message: '請輸入正確PORT (1 ~ 65535 之間)',
};
