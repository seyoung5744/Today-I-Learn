package com.zerobase.api.loan.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class EncryptComponent {

    private final String secretKey = "12314151315352657472625252";

    public String encryptString(String encryptString) {
        try {
            Cipher cipher = cipherPkcs5(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedString = cipher.doFinal(encryptString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decryptString(String dencryptString) {
        try {
            byte[] byteString = Base64.getDecoder().decode(dencryptString.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = cipherPkcs5(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(byteString));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher cipherPkcs5(int opMode, String secretKey) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec sk = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec iv = new IvParameterSpec(secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8));
        c.init(opMode, sk, iv);
        return c;
    }
}
