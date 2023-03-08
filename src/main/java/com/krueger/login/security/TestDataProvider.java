package com.krueger.login.security;

import com.krueger.login.repository.RoleRepository;
import com.krueger.login.model.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataProvider {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void addRole() {
        if (roleRepository.findByName("admin").isEmpty()) {
            roleRepository.save(
                    new Role("admin")
            );
        }
        if (roleRepository.findByName("user").isEmpty()) {
            roleRepository.save(
                    new Role("user")
            );
        }
    }
}
