import tel from "@/shared/validators/raw/tel";

export default function (isMobile: () => boolean) {
  return {
    $validator: tel(isMobile),
    $message: ({ $response }) => {
      if ($response !== null) {
        return isMobile()
          ? '請輸入正確手機格式，如：0910-123456'
          : '請輸入正確電話格式，如：02-23456789';
      }
    },
  }
};