package com.message.service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface SimpleJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    Optional<T> findById(ID id);
}
