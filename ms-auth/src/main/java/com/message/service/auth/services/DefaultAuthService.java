package com.message.service.auth.services;

import com.message.service.auth.entities.*;
import com.message.service.domain.entity.User;
import com.message.service.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultAuthService implements AuthService {

    @Autowired
    private UserRepository userRepository;

    private static HashMap<String, Authentication> tokens;

    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository
                .findByLogin(login)
                .map(u -> new DefaultAuthUser(u, collectUserAuthorities(u)))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find a user by the login"));
    }

    @Transactional
    public AuthUser getCurrentUser() {
        return Optional
                .of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(u -> u instanceof AuthUser)
                .map(u -> (AuthUser) u)
                .orElse(null);
    }

    public Map<String, Authentication> getTokens() {
        if (tokens == null) {
            tokens = new HashMap<>();
        }
        return tokens;
    }

    public boolean isAuthenticated() {

        return getCurrentUser() != null;
    }

    private List<GrantedAuthority> collectUserAuthorities(User user) {
        return user
                .getRolesStream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
