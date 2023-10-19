package tw.gov.pcc.eip.bpm.utils;

import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.bpm.services.AESKeyService;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;

@Service
public class AESEncryptionService {
    private static String key = null;

    private final AESKeyService aesKeyService;

    public AESEncryptionService(AESKeyService aesKeyService) {
        this.aesKeyService = aesKeyService;
    }

    public String encrypt(String id) throws Exception {
        String key = getKey();
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        byte[] encrypted = cipher.doFinal(id.getBytes("UTF-8"));
        return URLEncoder.encode(Base64.getEncoder().encodeToString(encrypted), "UTF-8");
    }

    public String getKey() {
        if (key != null) {

            return key;
        }

        return aesKeyService.getAESCode();

    }
}
