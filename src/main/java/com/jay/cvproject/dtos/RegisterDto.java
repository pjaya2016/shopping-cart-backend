package com.jay.cvproject.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterDto extends UserDto {
    private String password;
    private String secret;
}
