import enName from '@/shared/validators/raw/en-name';

export default function (name: string): boolean {
  let valid = false;
    if (chName(name)) valid = true;
    if (enName(name)) valid = true;
    if (blank(name)) valid = false;
  return valid;
}

// [\u4E00-\u9FFF]：中日韓統一表意文字列表，簡單來說，就是「所有的漢字」都在這個範圍裡，包含正體中文、簡體中文與日文、韓文、越南文裡的漢字。
// [\uD800-\uDFFF]：UTF-16使用區（包含所有難字...嗎？ㄅ曉得）
function aboriName(name: string): boolean {
  return /^[\u4E00-\u9FFF\uD800-\uDFFF]+[·•][\u4E00-\u9FFF\uD800-\uDFFF]+$/.test(name);
}

function chName(name: string): boolean {
  return /^[\u4E00-\u9FFF\uD800-\uDFFF]+$/.test(name);
}

function blank(name: string): boolean {
  return /\s/.test(name);
}

