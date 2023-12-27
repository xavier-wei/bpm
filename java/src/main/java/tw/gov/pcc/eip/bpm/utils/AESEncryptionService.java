package tw.gov.pcc.eip.bpm.utils;

import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.bpm.services.AESService;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AESEncryptionService {

    private final AESService aesService;

    public AESEncryptionService(AESService aesService) {
        this.aesService = aesService;
    }

    public String encrypt(String id) throws Exception {
        String key = getKey();
        byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 32);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        String cipherInstance = aesService.getBpmCipher();
        Cipher cipher = Cipher.getInstance(cipherInstance);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        byte[] encrypted = cipher.doFinal(id.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(Base64.getEncoder().encodeToString(encrypted), StandardCharsets.UTF_8);
    }

    public String getKey() {
        String key = aesService.getAESCode();
        return key+key;

    }
}
