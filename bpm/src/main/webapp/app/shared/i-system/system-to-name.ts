
export function systemToName(system?: string): string {
  if (system) {

    switch (system) {
      case '0':
        return '人事差勤系統'
      case '1':
        return 'AD帳號 : '
      case '2':
        return '公文管理系統腳色 : '
      case '3':
        return '會議室管理系統'
      case '4':
        return '電子郵件帳號 : '
      case '5':
        return '全球資訊網 (https://www.pcc.gov.tw)'
      case '6':
        return '會內資訊網站請先至home.pcc.gov.tw「帳號登入」點選「註冊」，填寫個人基本資料'
      case '7':
        return '政府電子採購網 : '
      case '8':
        return '技師與工程技術顧問公司管理資訊系統'
      case '9':
        return '公共工程案件審議資訊系統'
      case '10':
        return '災後復建工程經費審議及執行資訊系統'
      case '11':
        return '公共工程標案管理系統'
      case '12':
        return '本會其他資通系統'
    }
  }
  return '';

}
