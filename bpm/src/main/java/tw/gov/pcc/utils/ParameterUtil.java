package tw.gov.pcc.utils;

public class ParameterUtil {
    private static String token =null;

    private static String aesKey = null;

    private static String bpmCipher = null;
    private ParameterUtil() {
    }

    public static String getToken() {
        return token;
    }
    public static String getAesKey() {
        return aesKey;
    }
    public static String getBpmCipher() {
        return bpmCipher;
    }
    public static void setToken(String tOKEN) {
        token = tOKEN;
    }

    public static void setAesKey(String aESKey) {
        aesKey = aESKey;
    }

    public static void setBpmCipher(String bpmCipher) {
        ParameterUtil.bpmCipher = bpmCipher;
    }
}
