package com.jay.cvproject.models.recaptcha;

import lombok.Data;

import java.util.List;

@Data
public class RecaptchaValidationResponse {
    private String success;
    private List<String> errorCodes;
    private String challenge_ts;
    private String hostname;
}
