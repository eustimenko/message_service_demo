package com.message.service.domain.repository;

import com.message.service.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface UserRepository extends SimpleJpaRepository<User, Long> {

    @Query("SELECT friend FROM User user, User friend " +
            "WHERE user.id=:id " +
            "AND user.id!=friend.id " +
            "AND friend NOT MEMBER OF user.friends")
    List<User> findPossibleFriends(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE lower(u.login)=lower(:login)")
    Optional<User> findByLogin(@Param("login") String login);
}

