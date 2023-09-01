package tw.gov.pcc.domain;

public enum BpmIsmsServiceBeanNameEnum {
    L410("L410","L410Service"),
    L414("L414", "L414Service"),
    Additional("Additional","AdditionalService");


    private final String key;
    private final String serviceBeanName;

    BpmIsmsServiceBeanNameEnum(String key, String serviceBeanName) {
        this.key = key;
        this.serviceBeanName = serviceBeanName;
    }

    // getKeyByBeanName
    public static String getKeyByServiceBeanName(String serviceBeanName) {
        for (BpmIsmsServiceBeanNameEnum bpmIsmsServiceBeanNameEnum : BpmIsmsServiceBeanNameEnum.values()) {
            if (bpmIsmsServiceBeanNameEnum.serviceBeanName.equals(serviceBeanName)) {
                return bpmIsmsServiceBeanNameEnum.key;
            }
        }
        return null;
    }

    // getBeanNameByKey
    public static String getServiceBeanNameByKey(String key) {
        for (BpmIsmsServiceBeanNameEnum bpmIsmsServiceBeanNameEnum : BpmIsmsServiceBeanNameEnum.values()) {
            if (bpmIsmsServiceBeanNameEnum.key.equals(key)) {
                return bpmIsmsServiceBeanNameEnum.serviceBeanName;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getServiceBeanNameByKey("L410"));
    }

}
