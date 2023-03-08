package com.krueger.login.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // let spring know that this is the security configuration here
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/perform_registration","/auth/login",
                            "/auth/registration", "/css/*").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/perform/login")
                    .defaultSuccessUrl("/me/greeting")
                .and()
                .logout()
                    .logoutUrl("/perform_logout")
                    .logoutSuccessUrl("/auth/login")
                .and()
                .httpBasic()
                .and()
                .build();
    }
}
