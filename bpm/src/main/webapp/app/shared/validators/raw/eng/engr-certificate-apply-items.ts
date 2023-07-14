import { EngrCertificateApplyItemEnum } from "@/shared/enum";

export default function (applyItems: string[]): boolean {
  if (applyItems.includes(EngrCertificateApplyItemEnum.NEW_APPLY) && applyItems.includes(EngrCertificateApplyItemEnum.REISSUE)) return false;
  if (applyItems.includes(EngrCertificateApplyItemEnum.ABOLISH) && applyItems.length > 1) return false;
  return true;
}