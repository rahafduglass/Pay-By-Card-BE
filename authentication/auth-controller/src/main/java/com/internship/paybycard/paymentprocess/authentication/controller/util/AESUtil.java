package com.internship.paybycard.paymentprocess.authentication.controller.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY ="1234567890abcdef";

    public static String decrypt(String cipherText) {
        try{
            log.info("Decrypting cipherText: {}", cipherText);
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipher.DECRYPT_MODE, keySpec);
            byte[] decodedBytes = Base64.decodeBase64(cipherText);
            return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
