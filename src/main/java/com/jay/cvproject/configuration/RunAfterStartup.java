package com.jay.cvproject.configuration;

import com.jay.cvproject.models.Secrets;
import com.jay.cvproject.service.SecretService;
import com.jay.cvproject.utilities.Constants;

import java.security.NoSuchAlgorithmException;


public class RunAfterStartup {
    public RunAfterStartup(SecretService secretService) throws NoSuchAlgorithmException {
        secretService.save(new Secrets(Constants.APP_ID));
    }
}
