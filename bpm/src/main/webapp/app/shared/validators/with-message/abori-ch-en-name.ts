import aboriChEnName from "@/shared/validators/raw/abori-ch-en-name";

export default {
  $validator: aboriChEnName,
  $message: '請輸入正確姓名格式，如：「王小明」、「王·小明」、「王•小明」、「WANG,SIAO-MING」',
};
