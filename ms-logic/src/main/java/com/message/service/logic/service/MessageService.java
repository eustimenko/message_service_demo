package com.message.service.logic.service;

import com.message.service.domain.entity.Message;

import java.util.List;

public interface MessageService extends CrudService<Message> {

    List<Message> findByAuthor(Long idAuthor);

    List<Message> findByRecipient(Long idRecipient);
}
