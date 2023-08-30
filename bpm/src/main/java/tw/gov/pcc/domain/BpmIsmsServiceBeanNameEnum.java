package tw.gov.pcc.domain;

public enum BpmIsmsServiceBeanNameEnum {
    L414("L414", "L414Service");
    private final String key;
    private final String serviceBeanName;

    BpmIsmsServiceBeanNameEnum(String key, String serviceBeanName) {
        this.key = key;
        this.serviceBeanName = serviceBeanName;
    }

    // getDecisionByName
    public static String getKeyByServiceBeanName(String serviceBeanName) {
        for (BpmIsmsServiceBeanNameEnum bpmIsmsServiceBeanNameEnum : BpmIsmsServiceBeanNameEnum.values()) {
            if (bpmIsmsServiceBeanNameEnum.serviceBeanName.equals(serviceBeanName)) {
                return bpmIsmsServiceBeanNameEnum.key;
            }
        }
        return null;
    }

    // getNameByDecision
    public static String getServiceBeanNameByKey(String key) {
        for (BpmIsmsServiceBeanNameEnum bpmIsmsServiceBeanNameEnum : BpmIsmsServiceBeanNameEnum.values()) {
            if (bpmIsmsServiceBeanNameEnum.key.equals(key)) {
                return bpmIsmsServiceBeanNameEnum.serviceBeanName;
            } else {
                return key + "Service";
            }
        }
        return null;
    }

}
