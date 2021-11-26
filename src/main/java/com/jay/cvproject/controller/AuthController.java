package com.jay.cvproject.controller;

import com.google.zxing.WriterException;
import com.jay.cvproject.controller.interfaces.IAuthenticationFacade;
import com.jay.cvproject.dtos.RegisterDto;
import com.jay.cvproject.dtos.UserDto;
import com.jay.cvproject.service.UserService;
import com.jay.cvproject.service.auth.CustomUserDetailsService;
import com.jay.cvproject.utilities.AuthUtil;
import com.jay.cvproject.utilities.GoogleAuth;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
public class AuthController implements IAuthenticationFacade {

    private final AuthUtil authUtil;
    private final CustomUserDetailsService customerDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(AuthUtil authUtil, CustomUserDetailsService customerDetailsService, PasswordEncoder passwordEncoder, UserService userService) {
        this.authUtil = authUtil;
        this.customerDetailsService = customerDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserDto login(@RequestParam("email") String email
            , @RequestParam("password") String pwd
            , @RequestHeader("x-tfa") String authCode) {
        UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
        String secret = userService.getSecretOfUser(email);

        if (authCode.equals(GoogleAuth.getTOTPCode(secret))) {
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

    @PostMapping
    public boolean doesUserExists(@RequestParam("email") String email) {
        UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
        return userDetails != null;
    }

    @PostMapping("/register")
    public ResponseEntity<byte[]> register(@RequestBody RegisterDto registerDto) throws IOException, WriterException {


        UserDto userDetails = userService.findByEmail(registerDto.getEmail());

        if (!Objects.isNull(userDetails)) {
            throw new RuntimeException("User already exists!");
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userService.register2factor(registerDto));
    }

    @Override
    @GetMapping("/get-authenticated")
    public Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

}