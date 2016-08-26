package com.message.service.auth.entities;

import com.message.service.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthUser extends UserDetails {

    Long getId();

    String getLogin();

    String getFullname();

    String getEmail();

    User getUser();

    Boolean hasRole(String role);
}
