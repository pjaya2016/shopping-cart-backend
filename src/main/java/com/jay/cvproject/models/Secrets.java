package com.jay.cvproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
    private String salt;

    public Secrets(String appId) throws NoSuchAlgorithmException {
        this.appId = appId;
        this.secretKey = genSecretKey();
        this.salt = genNextSalt();
    }

    public String genNextSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return Base64
                .getEncoder()
                .encodeToString(bytes);
    }

    public String genSecretKey() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        return Base64
                .getEncoder()
                .encodeToString(secretKey.getEncoded());
    }
}
