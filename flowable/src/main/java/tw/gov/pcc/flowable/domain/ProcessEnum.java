package tw.gov.pcc.flowable.domain;

public enum ProcessEnum {
    L414("L414","ProcessL414");

    private String formName;
    private String processKey;

    ProcessEnum(String key, String processKey) {
        this.formName = key;
        this.processKey = processKey;
    }

    public static String getProcessKeyBykey(String formName) {
        for (ProcessEnum processEnum : ProcessEnum.values()) {
            if (processEnum.formName.equals(formName)) {
                return processEnum.processKey;
            }
        }
        return null;
    }

}
