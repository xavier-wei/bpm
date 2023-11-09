package tw.gov.pcc.flowable.config;

import lombok.Getter;

public class BpmSetting {
    @Getter
    private static String url=null;
    @Getter
    private static String token = null;

    private BpmSetting() {
    }

    public static void setUrl(String url) {
        BpmSetting.url = url;
    }

    public static void setToken(String token) {
        BpmSetting.token = token;
    }

}
