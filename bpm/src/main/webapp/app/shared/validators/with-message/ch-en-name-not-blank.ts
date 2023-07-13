import chEnNameNotBlank from "@/shared/validators/raw/ch-en-name-not-blank";

export default {
  $validator: chEnNameNotBlank,
  $message: '請輸入正確姓名格式，禁止輸入空格、括弧及其他特殊符號',
};
