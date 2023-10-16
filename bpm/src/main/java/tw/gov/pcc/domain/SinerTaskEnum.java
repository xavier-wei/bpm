package tw.gov.pcc.domain;

// 排序用
public enum SinerTaskEnum {
    APPLIER("申請人確認", "applier"),
    CHIEF("科長簽核", "sectionChief"),
    DIRECTOR("主管簽核", "director"),
    INFO_GROUP("資推小組承辦人員", "infoGroup"),
    SENIOR_SPECIALIST("簡任技正/科長簽核", "seniorTechSpecialist"),
    SERVER_ROOM_OPERATOR("機房操作人員", "serverRoomOperator"),
    REVIEW_STAFF("複核人員", "reviewStaff"),
    SERVER_ROOM_MANAGER("機房管理人員", "serverRoomManager"),
    HR_SYS("人事差勤系統", "HrSysSigner"),
    AD_SYS("AD帳號", "AdSysSigner"),
    OD_SYS("公文管理系統角色", "OdSysSigner"),
    MEETING_ROOM("會議室管理系統管理權限", "MeetingRoomSigner"),
    EMAIL_SYS("電子郵件帳號", "EmailSysSigner"),
    WEB_SITE("全球資訊網&會內資訊網", "WebSiteSigner"),
    PCC_PIS("政府電子採購網", "PccPisSigner"),
    REV_SYS("公共工程案件審議資訊系統", "RevSysSigner"),
    BID_SYS("公共工程標案管理系統", "BidSysSigner"),
    REC_SYS("災後復建工程經費審議及執行資訊系統", "RecSysSigner"),
    OTHER_SYS1("本會其他資通系統1", "OtherSys1Signer"),
    OTHER_SYS2("本會其他資通系統2", "OtherSys2Signer"),
    OTHER_SYS3("本會其他資通系統3", "OtherSys3Signer");

    private final String name;
    private final String task;

    SinerTaskEnum(String name, String task) {
        this.name = name;
        this.task = task;
    }

    public static String getTaskByName(String name) {
        for (SinerTaskEnum sinerTaskEnum : SinerTaskEnum.values()) {
            if (sinerTaskEnum.name.equals(name)) {
                return sinerTaskEnum.task;
            }
        }
        return null;
    }

    // getNameByDecision
    public static String getNameByTask(String task) {
        for (SinerTaskEnum sinerTaskEnum : SinerTaskEnum.values()) {
            if (sinerTaskEnum.task.equals(task)) {
                return sinerTaskEnum.name;
            }
        }
        return null;
    }

}
