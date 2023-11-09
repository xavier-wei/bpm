package tw.gov.pcc.flowable.domain;

public enum SupervisorSignerEnum {
    L414("ProcessL414", new String[]{"sectionChief", "director"}),
    L410("ProcessL410", new String[]{"sectionChief", "director"});
    private final String processKey;
    private final String[] supervisors;


    SupervisorSignerEnum(String processKey, String[] supervisors) {
        this.processKey = processKey;
        this.supervisors = supervisors;
    }

    public static String[] getSupervisors(String processKey) {
        for (SupervisorSignerEnum supervisorSignerEnum : SupervisorSignerEnum.values()) {
            if (supervisorSignerEnum.processKey.equals(processKey)) {
                return supervisorSignerEnum.supervisors;
            }
        }
        return new String[0];

    }


}
