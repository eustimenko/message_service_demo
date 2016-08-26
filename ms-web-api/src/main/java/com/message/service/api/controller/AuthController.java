package com.message.service.api.controller;

import com.google.common.base.Strings;
import com.message.service.auth.services.AuthService;
import com.message.service.auth.token.TokenEncryptor;
import com.message.service.domain.entity.User;
import com.message.service.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenEncryptor tokenEncryptor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public ResponseEntity<String> login(@RequestParam("login") String login, @RequestParam("password") String password) {
        final User user = userService.findByLogin(login);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            final UserDetails userDetails = authService.loadUserByUsername(login);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String rawUserToken = login + ";" + password;
            final String encryptedUserToken = tokenEncryptor.encrypt(rawUserToken);
            return new ResponseEntity<>(encryptedUserToken, HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "register")
    public ResponseEntity<String> register(@RequestBody User params) {
        if (isValid(params.getPassword(), params.getConfirmPassword())) {
            userService.create(params);
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            //TODO: add password not equals exception
        }
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    private boolean isValid(String password, String confirm) {
        if (Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(confirm)) return false;
        return password.equals(confirm);
    }
}
