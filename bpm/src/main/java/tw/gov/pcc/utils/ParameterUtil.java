package tw.gov.pcc.utils;

public class ParameterUtil {
    private static String token =null;

    private static String aesKey = null;

    private ParameterUtil() {
    }

    public static String getToken() {
        return token;
    }
    public static String getAesKey() {
        return aesKey;
    }
    public static void setToken(String tOKEN) {
        token = tOKEN;
    }
    public static void setAesKey(String aESKey) {
        aesKey = aESKey;
    }
}
