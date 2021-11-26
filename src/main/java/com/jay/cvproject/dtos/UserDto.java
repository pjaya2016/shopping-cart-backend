package com.jay.cvproject.dtos;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private String token;
    private int expiresIn;
    private boolean twoFactorEnabled;
}
