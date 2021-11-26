package com.jay.cvproject.configuration;

import com.jay.cvproject.models.Secrets;
import com.jay.cvproject.service.SecretService;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.event.EventListener;

import java.security.NoSuchAlgorithmException;


public class RunAfterStartup {


    private final SecretService secretService;

    public RunAfterStartup(SecretService secretService) {
        this.secretService = secretService;
    }

    @EventListener(ApplicationPreparedEvent.class)
    public void runAfterStartup() throws NoSuchAlgorithmException {
        secretService.save(new Secrets("JAY_CV_PROJECT"));

    }


}
