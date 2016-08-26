package com.message.service.domain.repository;

import com.message.service.domain.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface RoleRepository extends SimpleJpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name=:name")
    Optional<Role> findByName(@Param("name") String name) throws NoSuchElementException;
}
