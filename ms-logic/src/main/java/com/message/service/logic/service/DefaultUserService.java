package com.message.service.logic.service;

import com.google.common.collect.Sets;
import com.message.service.domain.entity.*;
import com.message.service.domain.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User params) throws NoSuchElementException {
        if (params.getRoles() == null || params.getRoles().isEmpty()) {
            params.replaceRoles(Sets.newHashSet(roleRepository.findByName(Role.USER).get()));
        }

        params.setPassword(passwordEncoder.encode(params.getPassword()));
        return userRepository.save(params);
    }

    public User update(Long id, User params) throws NoSuchElementException {
        final User user = userRepository.findById(id).get();

        user.setFullname(params.getFullname());
        user.setEmail(params.getEmail());
        user.setLogin(params.getLogin());

        return userRepository.save(user);
    }

    public void delete(Long id) throws NoSuchElementException {
        userRepository.delete(userRepository.findById(id).get());
    }

    public User findById(Long id) throws NoSuchElementException {
        final User user = userRepository.findById(id).get();
        Hibernate.initialize(user.getFriends());
        return user;
    }

    public void resetPassword(Long idUser, String password) throws NoSuchElementException {
        final User user = userRepository.findById(idUser).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void makeFriend(Long idUser, Long idFriend) throws NoSuchElementException {
        final User user = userRepository.findById(idUser).get();
        final Set<User> friends = user.getFriends();
        friends.add(userRepository.findById(idFriend).get());
        user.replaceFriends(friends);
    }

    public void deleteFriend(Long idUser, Long idFriend) throws NoSuchElementException {
        final User user = userRepository.findById(idUser).get();
        final Set<User> friends = user.getFriends();
        friends.remove(userRepository.findById(idFriend).get());
        user.replaceFriends(friends);
    }

    public List<User> getPossibleFriends(Long idUser) {
        return userRepository.findPossibleFriends(idUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void makeUserDefault(Long idUser) throws NoSuchElementException {
        final User user = userRepository.findById(idUser).get();
        if (user.hasRole(Role.ROOT)) {
            user.replaceRoles(Sets.newHashSet(roleRepository.findByName(Role.ROOT).get()));
        }
        userRepository.save(user);
    }

    public void makeUserRoot(Long idUser) throws NoSuchElementException {
        final User user = userRepository.findById(idUser).get();
        final Set<Role> roles = user.getRoles();
        if (!user.hasRole(Role.ROOT)) {
            roles.add(roleRepository.findByName(Role.ROOT).get());
            user.replaceRoles(roles);
        }
        userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) throws NoSuchElementException {
        return userRepository.findByLogin(login).get();
    }
}
