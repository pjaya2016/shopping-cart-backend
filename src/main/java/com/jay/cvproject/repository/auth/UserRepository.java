package com.jay.cvproject.repository.auth;

import com.jay.cvproject.models.auth.AuthUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}
