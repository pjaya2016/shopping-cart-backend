package com.jay.cvproject.repository;

import com.jay.cvproject.models.Secrets;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretRepository extends PagingAndSortingRepository<Secrets, Long> {
    Optional<Secrets> findByAppId(String appId);
}
