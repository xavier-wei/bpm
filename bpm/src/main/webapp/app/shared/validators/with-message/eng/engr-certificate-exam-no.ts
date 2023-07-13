import examNo from "@/shared/validators/raw/eng/engr-certificate-exam-no";

export default {
  $validator: examNo,
  $message: '括號內請輸入合理之年度，如：107',
};
