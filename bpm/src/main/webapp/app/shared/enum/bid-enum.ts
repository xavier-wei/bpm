export enum BidFormStatusEnum {
  CREATE = 'create',
  MODIFY = 'modify',
}

export enum BidExternalSiteEnum {
  //執業機構
  PRACTITIONER = 'https://pe2sys.pcc.gov.tw/Public/EGR/Report1.aspx?progid=EGR_2_2_1&Qtxt_SerialNum=',
  // 司法院「裁判書系統」
  REFEREE_SYSTEM = 'https://judgment.judicial.gov.tw/FJUD/Default_AD.aspx',
  // 全國建築管理資訊系統入口網
  CPAMI = 'http://cpabm.cpami.gov.tw/search/clis/TecInfo.jsp',
}

export enum BidMessageEnum {
  // 刪除提示訊息
  deleteMsg = '是否確認送出刪除內容？',
}

/**
 * 標案狀態
 */
export enum BidProjectStatusEnum {
  unfinished = 'unfinished', // 未竣工
  completed = 'completed', // 已竣工 未驗收
  accepted = 'accepted', // 已驗收 未結案
  closed = 'closed', // 已結案
}
