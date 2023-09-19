package tw.gov.pcc.domain;

public enum SingerEnum {
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
    SingerEnum(String name,String decision) {
        this.name = name;
        this.decision = decision;
    }

    // getDecisionByName
    public static String getDecisionByName(String name) {
        for (SingerEnum singerEnum : SingerEnum.values()) {
            if (singerEnum.name.equals(name)) {
                return singerEnum.decision;
            }
        }
        return null;
    }
    // getNameByDecision
    public static String getNameByDecision(String decision) {
        for (SingerEnum singerEnum : SingerEnum.values()) {
            if (singerEnum.decision.equals(decision)) {
                return singerEnum.name;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getDecisionByName("簡任技正/科長簽核"));
    }
}



