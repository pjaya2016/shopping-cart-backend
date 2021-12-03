package com.jay.cvproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Secrets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String appId;
    private String secretKey;
    private byte[] generateIv;

    public Secrets(String appId) throws NoSuchAlgorithmException {
        this.appId = appId;
        this.secretKey = genSecretKey();
        this.generateIv = generateIv().getIV();
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private String genSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
    }
}
