export enum RoleEnum {
  USER = '使用者',
  ADMIN = '管理員',
  // Tim加的
  COMP = '顧問公司',
}

export enum EngRoleEnum {
  ENGR = 'ENG_01',              //技師
  COMPANY = 'ENG_02',           //顧問公司
  GOV_ORGANIZATION = 'ENG_03',  //政府機關
  GUILD = 'ENG_04',             //公會
  FEDERATION = 'ENG_05',        //全聯會
  COMP_GUILD = 'ENG_06',        //顧問公會
  OTHER_GROUPS = 'ENG_07',      //其他團體
  ADMIN = 'ENG_08',             //業管人員
}

export enum FormStatusEnum {
  READONLY = '檢視',
  EDIT = '編輯',
  CREATE = '1',       // 新增
  REAPPLY = '2',      // 變更申請
  REMAKE = '3',       // 新增另案
  PATCH = '4',        // 補登
  REVIEW = '審核'
}

export enum MemberTypeEnum {
  ENGR = '0',           // 技師
  CONTRACTOR = '1',     // 承辦人
  MASTER = '2',         // 公司負責人
  REPRESENTATIVE = '3'  // 其它團體代表人
}

export enum CompTypeEnum {
  OFFICE = '0',             // 事務所
  COMPANY = '1',            // 顧問公司
  GUILD = '2',              // 公會
  GOV_ORGANIZATION = '3',   // 政府組織
  REGIONAL_AUTHORITY = '4', // 區域主管機關
  FEDERATION = '5',         // 全聯會
  COMP_GUILD = '6',         // 顧問公會
  DISCIPLINE_UNIT = '7',    // 懲戒移送單位
  OTHER_GROUPS = '8',       // 其他團體
  ARCHITECT_OFFICE = '9',   // 建築師事務所
}

export enum EbillingChecksDocTypeEnum {
  ENGR_CERTIFICATE = '1', // 技師證書
  ENGR_LICENSE = '2',     // 技師執業執照
  CST_LICENSE = '3',      // 顧問公司許可登記
}

export enum ReviewCaseTypeEnum {
  // ENGR_DISCIPLINE = '4',  // 技師懲戒
  // ENGR_SUBJECTPOINT = '7',// 技師書面積分審查
  COMP_CHECK = '8',       // 事務所/公司查核
  // YEARREPORT = '9',       // 年度業務報告
  ENGR_CERTIFICATE = '1', // 技師證書
  ENGR_LICENSE = '2',     // 技師執業執照
  CST_LICENSE = '3',      // 顧問公司許可登記
  COURSE = '4',           // 課程主檔
  ENGRCLASS_RECORD = '5', // 技師上課紀錄
}

export enum StatusEnum {
  // REVIEW1 = '2',    // 待審核(一關)
  // REVIEW2_1 = '3',  // 待審核(二關)
  // REVIEW2_2 = '4',  // 審核中(二關)
  DISCARD = '0',    // 作廢
  UNREVIEW = '1',   // 未審核/待審核
  REVIEWING = '2',  // 待審核/審核中
  CLOSE = '3',      // 已結案/已審核/有效
  RETURN = '4',     // 批退
  TEMP = '5',       // 暫存
}

export enum ReviewStatusEnum {
  APPLY = '1',    // 申請
  REAPPLY = '2',  // 重新申請
  APPROVE = '3',  // 核准
  RETURN = '4',   // 核退
  CANCEL = '5',   // 取消核准
  DISCARD = '6',  // 作廢
}

export enum ReviewActionEnum {
  APPLY = '1',    // 申請
  APPROVE = '2',  // 核准
  RETURN = '3',   // 核退
  CANCEL = '4',   // 取消核准
  DISCARD = '5',  // 作廢
}

export enum ApplyTypeEnum {
  PAPER_APPLY = '0',   // 紙本申請
  ONLINE_APPLY = '1',  // 線上申請
}

export enum EngrCertificateApplyItemEnum {
  NEW_APPLY = '0',  // 新申請
  APPLY_EN = '1',   // 申請英文證明書
  REISSUE = '2',    // 補發
  CHANGE = '3',    // 變更（只存在於舊資料，不在新系統的申請項目裡）
  ABOLISH = '4',    // 廢止
}

export enum ReceiveTypeEnum {
  PERMANENT = '0',  // 戶籍地址
  CONTACT = '1',    // 通訊地址
  COMPANY = '2',    // 公司地址
  SELF = '3',       // 自取
  OTHER = '4',      // 其他地址
}

export enum SexEnum {
  FEMALE = '0',   // 女
  MALE = '1',     // 男
}

export enum IdentityTypeEnum {
  NORMAL = '0',       // 一般
  INDIGENOUS = '1',   // 原住民
  FOREIGN = '2',      // 外國籍
}

export enum ApplyItemEnum102 {
  NEW_APPLY = '0',              // 新申請
  CHANGE = '1',                 //變更
  CHANGE_ISSUED = '2',          //換發
  CHANGE_PHOTO = '3',           //換照
  REISSUE = '4',                // 補發
  SELF_CLOSING = '5',           //自行停業
  CHANGES_REPLACEMENTS = '6',   //變更及換發
  ABOLISH = '7',                // 廢止
  APPLY_EN = '8',               // 申請英文證明書
}

export enum CompMemberTypeEnum {
  ENGR = '0',                 // 技師
  MANAGER = '1',              // 經理人
  DIRECTOR_OR_EXECUTOR = '2', // 董事或執行業務
  GUILD = '3',                // 公會
  COMPANY = '4',              // 顧問公司
}

export enum ECLApplyItemEnum {
  LICENSE = '0', // 許可
  LICENSE_CHANGE = '1', // 許可變更
  LICENSE_CHANGE_SUBJECT = '2', // 許可變更科別
  LICENSE_CHANGE_DIRECTORS = '3', // 許可變更董事
  LICENSE_CHANGE_MONITOR = '4', // 許可變更監察人
  ISSUED = '7', // 核發
  CHANGE_ISSUED = '8', // 換發
  REISSUED = '10', // 補發
  LOG_OUT = '11', // 註銷
  SELF_CLOSE = '12', // 自行停業
  SUSPENSION = '13', // 停業處分
  REVOKE = '14', // 撤銷或廢止登記
  REVOKE_LICENSE_CHANGE = '15', // 廢止變更許可
  RESUME = '16', // 復業
  REVOKE_LICENSE = '17', // 廢止許可
  ISSUED_EN = '20', // 核發英文登記證
}

export enum YearreportFillStatusEnum {
  UNFILLED = '0',    // 未填
  INCOMPLETE = '1',  // 有填未完整
  COMPLETE = '2',    // 完整
}
