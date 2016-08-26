package com.message.service.auth.services;

import com.message.service.auth.entities.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * Responds to authentication.
 * Provide information about authenticated user.
 */
public interface AuthService extends UserDetailsService {

    AuthUser getCurrentUser();

    boolean isAuthenticated();

    Map<String, Authentication> getTokens();
}
