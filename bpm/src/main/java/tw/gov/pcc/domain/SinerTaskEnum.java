package tw.gov.pcc.domain;

public enum SinerTaskEnum {
    APPLIER("申請人確認","applier"),
    CHIEF("科長簽核","sectionChief"), // 0、1、2
    DIRECTOR("主管簽核","director"), // 0、1、2
    INFO_GROUP("資推小組承辦人員","infoGroup"), // 0、1
    SENIOR_SPECIALIST("簡任技正/科長簽核","seniorTechSpecialist"),
    SERVER_ROOM_OPERATOR("機房操作人員","serverRoomOperator"),
    REVIEW_STAFF("複核人員", "reviewStaff"),
    SERVER_ROOM_MANAGER("機房管理人員", "serverRoomManager");

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
