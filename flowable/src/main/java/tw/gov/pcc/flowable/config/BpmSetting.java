package tw.gov.pcc.flowable.config;

public class BpmSetting {
    private static String url=null;
    private static String token = null;

    private BpmSetting() {
    }

    public static void setUrl(String url) {
        BpmSetting.url = url;
    }

    public static void setToken(String token) {
        BpmSetting.token = token;
    }

    public static String getUrl() {
        return url;
    }

    public static String getToken() {
        return token;
    }
}
