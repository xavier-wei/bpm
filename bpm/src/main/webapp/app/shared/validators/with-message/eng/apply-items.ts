import applyItems from '../../raw/eng/apply-items';
import { EngrCertificateApplyItemEnum } from '@/shared/enum';

export default {
  $validator: applyItems,
  $message: ({ $model }) => {
    if ($model && $model.includes(EngrCertificateApplyItemEnum.NEW_APPLY) && $model.includes(EngrCertificateApplyItemEnum.REISSUE)) {
      return '不得同時選取［新申請］及［補發］';
    }
  },
};
