package tw.gov.pcc.eip.bpm.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;

public class AESEncryption {
    public static String encrypt(String id, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        byte[] encrypted = cipher.doFinal(id.getBytes("UTF-8"));
        return URLEncoder.encode(Base64.getEncoder().encodeToString(encrypted), "UTF-8");
    }
}
