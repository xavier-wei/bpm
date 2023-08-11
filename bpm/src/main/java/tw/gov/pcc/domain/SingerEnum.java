package tw.gov.pcc.domain;

public enum SingerEnum {
    CHIEF("科長簽核","chiefDecision"),
    DIRECTOR("主管簽核","directorDecision"),
    SENIOR_TECH_SPECIALIST("簡任技正/科長簽核","seniorTechSpecialistDecision");

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
