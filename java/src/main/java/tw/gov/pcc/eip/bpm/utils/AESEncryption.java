package tw.gov.pcc.eip.bpm.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Base64;

public class AESEncryption {
    public static String encrypt(String id) throws Exception {
        String key = getKey();
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        byte[] encrypted = cipher.doFinal(id.getBytes("UTF-8"));
        return URLEncoder.encode(Base64.getEncoder().encodeToString(encrypted), "UTF-8");
    }

    public static String getKey() {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = AESEncryption.class.getClassLoader();
        try (InputStream in = classLoader.getResourceAsStream("AES.json")) {
            JsonNode jsonNode = objectMapper.readTree(in);
            return jsonNode.get("key").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
