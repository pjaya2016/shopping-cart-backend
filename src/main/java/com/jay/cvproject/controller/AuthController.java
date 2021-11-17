package com.jay.cvproject.controller;

import com.jay.cvproject.controller.interfaces.IAuthenticationFacade;
import com.jay.cvproject.dtos.RegisterDto;
import com.jay.cvproject.dtos.UserDto;
import com.jay.cvproject.service.UserService;
import com.jay.cvproject.service.auth.CustomUserDetailsService;
import com.jay.cvproject.utilities.AuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public UserDto login(@RequestParam("email") String email, @RequestParam("password") String pwd) {
        UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
        boolean result = passwordEncoder.matches(pwd, userDetails.getPassword());
        if (result) {
            UserDto dto = userService.findByEmail(email);
            dto.setToken(authUtil.getToken(email));
            dto.setExpiresIn(dto.getExpiresIn());
            return dto;
        } else {
            throw new RuntimeException("Login Details is in correct");
        }
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterDto registerDto) {
        return userService.save(registerDto);
    }

    @Override
    @GetMapping("/get-authenticated")
    public Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

}