package com.message.service.auth.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.message.service.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class DefaultAuthUser implements AuthUser {

    private User user;
    private ArrayList<GrantedAuthority> authorities;

    public DefaultAuthUser(User user, List<GrantedAuthority> authorities) {
        if (user == null || authorities == null)
            throw new NullPointerException();
        this.user = user;
        this.authorities = new ArrayList<>(authorities);
    }

    public DefaultAuthUser(User user) {
        if (user == null)
            throw new NullPointerException();
        this.user = user;
        this.authorities = new ArrayList<>();
    }

    public Long getId() {
        return user.getId();
    }

    public String getLogin() {
        return user.getLogin();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getFullname() {
        return user.getFullname();
    }

    // Implementation of UserDetails interface

    @JsonIgnore // secured
    public String getPassword() {
        return user.getPassword();
    }

    @JsonIgnore // use login instead of this
    public String getUsername() {
        return user.getLogin();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    @JsonIgnore
    public Boolean hasRole(String role) {
        return user.hasRole(role);
    }
}
