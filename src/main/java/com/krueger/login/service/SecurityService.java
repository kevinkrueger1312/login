package com.krueger.login.service;

import com.krueger.login.model.Role;
import com.krueger.login.model.UserEntity;
import com.krueger.login.repository.RoleRepository;
import com.krueger.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    // return true when username is taken
    public boolean isUsernameTaken(UserEntity userForm) {
        return userRepository.existsByUsername(userForm.getUsername());
    }


    // save new user
    public void saveNewUser(UserEntity userForm) {
        // when the userForm doesn't exist
        UserEntity newUser = new UserEntity();

        newUser.setUsername(userForm.getUsername());
        // password will be encrypted before it is stored in the database
        newUser.setPassword(passwordEncoder.encode(userForm.getPassword()));

        // a list of GrantedAuthority objects is created from the user's list of roles
        // and by default a new user is assigned the role "user"
        Optional<Role> role = roleRepository.findByName("user");
        role.ifPresent(value -> newUser.setRoles(Collections.singletonList(value)));

        userRepository.save(newUser);
    }


    public void remindUser(UserEntity userLoginForm) {
        // takes an Authentication object as input and returns an Authentication object representing the authenticated user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginForm.getUsername(),
                userLoginForm.getPassword()
        ));
        // the user's security information is retained for the duration of the request processing.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
