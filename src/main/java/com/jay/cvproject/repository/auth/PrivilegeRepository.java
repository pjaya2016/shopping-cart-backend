package com.jay.cvproject.repository.auth;

import com.jay.cvproject.models.auth.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Privilege findByName(String name);
}
