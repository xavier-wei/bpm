package tw.gov.pcc.domain;

public enum SingerEnum {
    CHIEF("科長簽核","chiefDecision"), // 0、1、2
    DIRECTOR("主管簽核","directorDecision"), // 0、1、2
    INFO_GROUP("資推小組承辦人員","infoGroupDecision"); // 0、1

    private String name;
    private String decision;
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
}



