package com.jay.cvproject.service;

import com.jay.cvproject.models.Secrets;
import com.jay.cvproject.repository.SecretRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecretService {
    private final SecretRepository secretRepository;


    public SecretService(SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    public Secrets save(Secrets entity) {
        return secretRepository.save(entity);
    }

    public Secrets findByAppId(String appId) {
        Optional<Secrets> app = secretRepository.findByAppId(appId);
        return app.orElse(null);
    }
}
