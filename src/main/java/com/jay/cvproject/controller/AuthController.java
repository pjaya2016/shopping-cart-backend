package com.jay.cvproject.controller;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.jay.cvproject.controller.interfaces.IAuthenticationFacade;
import com.jay.cvproject.dtos.RegisterDto;
import com.jay.cvproject.dtos.UserDto;
import com.jay.cvproject.models.recaptcha.RecaptchaToken;
import com.jay.cvproject.models.recaptcha.RecaptchaValidationResponse;
import com.jay.cvproject.service.UserService;
import com.jay.cvproject.service.auth.CustomUserDetailsService;
import com.jay.cvproject.utilities.AES;
import com.jay.cvproject.utilities.AuthUtil;
import com.jay.cvproject.utilities.GoogleAuth;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
public class AuthController implements IAuthenticationFacade {

    private final AuthUtil authUtil;
    private final CustomUserDetailsService customerDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    public static final String KET_SECRET = "KET_SECRECT";
    private final AES aes;

    public AuthController(AuthUtil authUtil, CustomUserDetailsService customerDetailsService, PasswordEncoder passwordEncoder, UserService userService, AES aes) {
        this.authUtil = authUtil;
        this.customerDetailsService = customerDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.aes = aes;
    }

    @PostMapping
    public boolean doesUserExists(@RequestParam("email") String email) {
        UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
        return userDetails != null;
    }

    @PostMapping("/login")
    public UserDto login(@RequestParam("email") String email
            , @RequestParam("password") String pwd
            , @RequestHeader("x-tfa") String authCode) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
        String secret = userService.getSecretOfUser(email);

        if (authCode.equals(GoogleAuth.getTOTPCode(aes.decrypt(secret)))) {
            boolean result = passwordEncoder.matches(pwd, userDetails.getPassword());
            if (result) {
                UserDto dto = userService.findByEmail(email);
                dto.setToken(authUtil.getToken(email));
                dto.setExpiresIn(dto.getExpiresIn());
                return dto;
            } else {
                throw new RuntimeException("Login Details is in correct");
            }
        } else {
            throw new RuntimeException("Invalid 2FA Code");
        }

    }

    @Override
    @GetMapping("/get-authenticated")
    public Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    @PostMapping("/register")
    public ResponseEntity<byte[]> register(@RequestBody RegisterDto registerDto) throws IOException, WriterException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {


        UserDto userDetails = userService.findByEmail(registerDto.getEmail());

        if (!Objects.isNull(userDetails)) {
            throw new RuntimeException("User already exists!");
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userService.register2factor(registerDto));
    }

    @PostMapping("/recaptcha/verify")
    public RecaptchaValidationResponse verifyCode(@RequestBody RecaptchaToken recaptchaToken) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("secret", KET_SECRET));
        params.add(new BasicNameValuePair("response", recaptchaToken.getResponse()));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(httpPost);
        String res = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            byte[] bytes = IOUtils.toByteArray(instream);
            res = new String(bytes, StandardCharsets.UTF_8);
            instream.close();
        }
        client.close();
        Gson g = new Gson();
        return g.fromJson(res, RecaptchaValidationResponse.class);
    }

}