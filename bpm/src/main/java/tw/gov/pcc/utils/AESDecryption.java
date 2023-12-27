package tw.gov.pcc.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AESDecryption {
    private AESDecryption() {
    }

    public static String decrypt(String encryptedText) throws Exception {
        String key = ParameterUtil.getAesKey();
        key=key+key;
        byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 32);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        String cipherInstance = ParameterUtil.getBpmCipher();
        Cipher cipher = Cipher.getInstance(cipherInstance);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(original, StandardCharsets.UTF_8);
    }
}
