package com.message.service.logic.service;

import com.message.service.domain.entity.User;

import java.util.*;

public interface UserService extends CrudService<User> {

    void resetPassword(Long idUser, String password) throws NoSuchElementException;

    void makeFriend(Long idUser, Long idFriend) throws NoSuchElementException;

    void deleteFriend(Long idUser, Long idFriend) throws NoSuchElementException;

    List<User> getPossibleFriends(Long idUser);

    List<User> findAll();

    void makeUserRoot(Long idUser) throws NoSuchElementException;

    void makeUserDefault(Long idUser) throws NoSuchElementException;

    User findByLogin(String login) throws NoSuchElementException;
}
