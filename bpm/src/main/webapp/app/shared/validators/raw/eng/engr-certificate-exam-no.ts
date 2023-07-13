export default function (examNo: string): boolean {
  if (examNo && examNo.indexOf('(') !== -1 && examNo.indexOf(')') !== -1) {
    const yyy = examNo.substring(examNo.indexOf('(') + 1, examNo.indexOf(')'));
    return /^\d{1,3}$/.test(yyy) && Number(yyy) >= 0 && Number(yyy) <= (new Date().getFullYear() - 1911);
  }
  return true;
}