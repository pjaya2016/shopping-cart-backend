package com.jay.cvproject.utilities;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class Constants {

    public static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String APP_ID = "JAY_CV_PROJECT";
    public static final String algorithm = "AES/CBC/PKCS5Padding";
}
