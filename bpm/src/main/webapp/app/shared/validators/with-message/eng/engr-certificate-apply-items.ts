import engrCertificateApplyItems from "../../raw/eng/engr-certificate-apply-items";
import { EngrCertificateApplyItemEnum } from "@/shared/enum";

export default {
  $validator: engrCertificateApplyItems,
  $message: ({ $model }) => {
    if ($model && $model.includes(EngrCertificateApplyItemEnum.NEW_APPLY) && $model.includes(EngrCertificateApplyItemEnum.REISSUE)) {
      return '不得同時選取［新申請(雙語)］及［補發］';
    }
    if ($model.includes(EngrCertificateApplyItemEnum.ABOLISH) && $model.length > 1) {
      return '［廢止］不得與其它項目同時選取';
    }
  }
};