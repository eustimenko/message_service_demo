package com.message.service.domain.repository;

import com.message.service.domain.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends SimpleJpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.author.id=:idAuthor")
    List<Message> findByAuthor(@Param("idAuthor") Long idAuthor);

    @Query("SELECT m FROM Message m WHERE m.recipient.id=:idRecipient")
    List<Message> findByRecipient(@Param("idRecipient") Long idRecipient);
}
