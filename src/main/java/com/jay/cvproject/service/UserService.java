package com.jay.cvproject.service;

import com.jay.cvproject.dtos.RegisterDto;
import com.jay.cvproject.dtos.UserDto;
import com.jay.cvproject.mappers.UserMapper;
import com.jay.cvproject.models.auth.Privilege;
import com.jay.cvproject.models.auth.Role;
import com.jay.cvproject.models.auth.User;
import com.jay.cvproject.repository.auth.PrivilegeRepository;
import com.jay.cvproject.repository.auth.RoleRepository;
import com.jay.cvproject.repository.auth.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto save(RegisterDto dto) {
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User entity = userMapper.dtoToEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRoles(Collections.singletonList(adminRole));
        return userMapper.entityToDto(userRepository.save(entity));
    }

    public UserDto findByEmail(String email) {
        User entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email does not exists"));
        return userMapper.entityToDto(entity);
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}