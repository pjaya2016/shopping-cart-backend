package com.jay.cvproject.utilities;

import com.jay.cvproject.models.Secrets;
import com.jay.cvproject.service.SecretService;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private final Secrets secrets;

    public AES(SecretService service) {
        this.secrets = service.findByAppId(Constants.APP_ID);
    }


    public String encrypt(String strToEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(Constants.algorithm);
        IvParameterSpec t = new IvParameterSpec(secrets.getGenerateIv());

        byte[] decodedKey = Base64.getDecoder().decode(secrets.getSecretKey());

        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, originalKey, t);
        byte[] cipherText = cipher.doFinal(strToEncrypt.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public String decrypt(String strToDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(Constants.algorithm);
        IvParameterSpec iv = new IvParameterSpec(secrets.getGenerateIv());
        byte[] decodedKey = Base64.getDecoder().decode(secrets.getSecretKey());
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher.init(Cipher.DECRYPT_MODE, originalKey, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(strToDecrypt));
        return new String(plainText);
    }
}
