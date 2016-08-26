package com.message.service.domain.repository;

import org.springframework.data.repository.*;

import java.io.Serializable;
import java.util.*;

@NoRepositoryBean
public interface SimpleRepository<T, ID extends Serializable> extends Repository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T entity);

    void delete(T entity);
}
