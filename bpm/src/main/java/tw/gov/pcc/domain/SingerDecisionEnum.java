package tw.gov.pcc.domain;

// taskName用
public enum SingerDecisionEnum {
    APPLIER("申請人確認","applierConfirm"),
    CHIEF("科長簽核","sectionChiefDecision"), // 0、1、2
    DIRECTOR("主管簽核","directorDecision"), // 0、1、2
    INFO_GROUP("資推小組承辦人員","infoGroupDecision"), // 0、1
    SENIOR_SPECIALIST("簡任技正/科長簽核","seniorTechSpecialistSign"),
    SERVER_ROOM_OPERATOR("機房操作人員","serverRoomOperatorSetting"),
    REVIEW_STAFF("複核人員", "reviewStaff"),
    SERVER_ROOM_MANAGER("機房管理人員", "serverRoomManager"),
    HR_SYS("人事差勤系統", "HrSysSigner"),
    AD_SYS("AD帳號", "AdSysSigner"),
    OD_SYS("公文管理系統角色", "OdSysSigner"),
    MEETING_ROOM("會議室管理系統管理權限", "MeetingRoomSigner"),
    EMAIL_SYS("電子郵件帳號", "EmailSysSigner"),
    WEB_SITE("全球資訊網", "WebSiteSigner"),
    PCC_PIS("政府電子採購網", "PccPisSigner"),
//    ENG_AND_PRJ_INFO_SYS("技師與工程技術顧問公司管理資訊系統", "EngAndPrjInfoSysSigner"),
    REV_SYS("公共工程案件審議資訊系統", "RevSysSigner"),
    BID_SYS("公共工程標案管理系統", "BidSysSigner"),
    REC_SYS("災後復建工程經費審議及執行資訊系統", "RecSysSigner"),
    OTHER_SYS1("本會其他資通系統1", "OtherSys1Signer"),
    OTHER_SYS2("本會其他資通系統2", "OtherSys2Signer"),
    OTHER_SYS3("本會其他資通系統3", "OtherSys3Signer"),
    ADDITIONAL("加簽","AdditionalSigner");
    private final String name;
    private final String decision;
    SingerDecisionEnum(String name, String decision) {
        this.name = name;
        this.decision = decision;
    }

    // getDecisionByName
    public static String getDecisionByName(String name) {
        for (SingerDecisionEnum singerDecisionEnum : SingerDecisionEnum.values()) {
            if (singerDecisionEnum.name.equals(name)) {
                return singerDecisionEnum.decision;
            }
        }
        return null;
    }
    // getNameByDecision
    public static String getNameByDecision(String decision) {
        for (SingerDecisionEnum singerDecisionEnum : SingerDecisionEnum.values()) {
            if (singerDecisionEnum.decision.equals(decision)) {
                return singerDecisionEnum.name;
            }
        }
        return null;
    }

}



