package com.message.service.auth;

import com.message.service.auth.services.*;
import com.message.service.auth.token.TokenEncryptor;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan
public class AuthConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthService authService() {
        return new DefaultAuthService();
    }

    @Bean
    public TokenEncryptor tokenEncryptor() {
        return new TokenEncryptor();
    }
}
