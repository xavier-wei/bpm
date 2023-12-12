/**
 * 這個函式將提供的簽核角色轉換成表單簽核紀錄說明欄字串符，以便進行後續的處理或存儲。
 * @param taskDirections - 簽核角色
 * @returns 表單簽核紀錄說明欄字串
 */
export function changeDirections(taskDirections: any): string {

  switch (taskDirections) {
    case 'applierConfirm':
      return '申請者';
    case 'sectionChiefDecision':
      return '申請人所屬之科長';
    case 'directorDecision':
      return '申請人所屬之單位主管';
    case 'infoGroupDecision':
      return '資推小組承辦人員';
    case 'seniorTechSpecialistSign':
      return '資訊小組簡任技正/科長';
    case 'serverRoomOperatorSetting':
      return '機房操作人員';
    case 'reviewStaff':
      return '複核人員';
    case 'serverRoomManager':
      return '機房管理人員';
    case 'HrSysSigner':
      return '人事差勤系統';
    case 'AdSysSigner':
      return 'AD帳號';
    case 'OdSysSigner':
      return '公文管理系統';
    case 'MeetingRoomSigner':
      return '會議室管理系統管理權限';
    case 'EmailSysSigner':
      return '電子郵件帳號';
    case 'WebSiteSigner':
      return '全球資訊網';
    case 'PccPisSigner':
      return '政府電子採購網';
    case 'RevSysSigner':
      return '公共工程案件審議資訊系統';
    case 'BidSysSigner':
      return '公共工程標案管理系統';
    case 'RecSysSigner':
      return '災後復建工程經費審議及執行資訊系統';
    case 'OtherSys1Signer':
      return '本會其他資通系統1';
    case 'OtherSys2Signer':
      return '本會其他資通系統2';
    case 'OtherSys3Signer':
      return '本會其他資通系統3';
    case 'AdditionalSigner':
      return '加簽';
    default:
      return '';
  }
}

/**
 * 代號中文轉換元件
 * @param codeNo 主代號
 * @param selectList 存在store裡面的下拉選單資訊
 * @returns 轉化後的中文名稱
 */
export function changeCodeNoToCh(codeNo: any, selectList: any): string {
  if (codeNo == null) return '';
  let codeNoName = '';
  selectList.forEach(data => {

    if (data.value === codeNo) {
      codeNoName = data.text
    }
  })
  return codeNoName;
}


