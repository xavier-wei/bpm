package tw.gov.pcc.domain;

public enum SingerDecisionEnum {
    APPLIER("申請人確認","applierConfirm"),
    CHIEF("科長簽核","sectionChiefDecision"), // 0、1、2
    DIRECTOR("主管簽核","directorDecision"), // 0、1、2
    INFO_GROUP("資推小組承辦人員","infoGroupDecision"), // 0、1
    SENIOR_SPECIALIST("簡任技正/科長簽核","seniorTechSpecialistSign"),
    SERVER_ROOM_OPERATOR("機房操作人員","serverRoomOperatorSetting"),
    REVIEW_STAFF("複核人員", "reviewStaff"),
    SERVER_ROOM_MANAGER("機房管理人員", "serverRoomManager");

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



