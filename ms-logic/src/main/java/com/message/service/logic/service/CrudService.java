package com.message.service.logic.service;

import java.util.NoSuchElementException;

interface CrudService<T> {

    T create(T params);

    T update(Long id, T params) throws NoSuchElementException;

    void delete(Long id) throws NoSuchElementException;

    T findById(Long id) throws NoSuchElementException;
}
