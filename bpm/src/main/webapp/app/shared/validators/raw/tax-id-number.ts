export default function (taxNo: string): boolean {
  const array = [1, 2, 1, 2, 1, 2, 4, 1];
  const singalTaxNo = taxNo.split('');
  function check(num) {
    let total = num;
    if (total > 9) {
      let s = total.toString();
      const n1 = s.substring(0, 1) * 1;
      const n2 = s.substring(1, 2) * 1;
      total = n1 + n2;
    }
    return total;
  }
  let sum = 0;
  singalTaxNo.forEach((item, index) => {
    sum += check(parseInt(item) * array[index]);
  });
  if (taxNo.length !== 8) {
    return false;
  // 為相容新舊統一編號，檢查邏輯由可被『10』整除改為可被『5』整除
  } else if (sum % 5 === 0) {
    return true;
  } else if (singalTaxNo[6] === '7' && (sum + 1) % 10 === 0) {
    return true;
  } else {
    return false;
  }
}
