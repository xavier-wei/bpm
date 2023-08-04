package tw.gov.pcc.flowable.domain;

public enum ProcessEnum {
    L414("L414","ProcessL414");

    private final String formName;
    private final String processKey;

    ProcessEnum(String key, String processKey) {
        this.formName = key;
        this.processKey = processKey;
    }

    public static String getProcessKeyByKey(String formName) {
        for (ProcessEnum processEnum : ProcessEnum.values()) {
            if (processEnum.formName.equals(formName)) {
                return processEnum.processKey;
            }
        }
        return null;
    }
    // getFormNameByProcessKey
    public static String getFormNameByProcessKey(String processKey) {
        for (ProcessEnum processEnum : ProcessEnum.values()) {
            if (processEnum.processKey.equals(processKey)) {
                return processEnum.formName;
            }
        }
        return null;
    }
}