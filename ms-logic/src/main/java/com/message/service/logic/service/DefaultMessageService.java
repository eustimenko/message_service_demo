package com.message.service.logic.service;

import com.message.service.auth.services.AuthService;
import com.message.service.domain.entity.Message;
import com.message.service.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DefaultMessageService implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public Message create(Message params) throws NoSuchElementException {
        final Message message = new Message();

        message.setTitle(params.getTitle());
        message.setText(params.getText());
        message.setAuthor(authService.getCurrentUser().getUser());
        message.setRecipient(userRepository.findById(params.getRecipient().getId()).get());

        return messageRepository.save(message);
    }

    public Message update(Long id, Message params) throws NoSuchElementException {
        final Message message = messageRepository.findById(id).get();

        message.setRecipient(userRepository.findById(params.getRecipient().getId()).get());
        message.setAuthor(authService.getCurrentUser().getUser());
        message.setText(params.getText());
        message.setTitle(params.getTitle());

        return messageRepository.save(message);
    }

    public void delete(Long id) throws NoSuchElementException {
        messageRepository.delete(messageRepository.findById(id).get());
    }

    public List<Message> findByAuthor(Long idAuthor) {
        return messageRepository.findByAuthor(idAuthor);
    }

    public List<Message> findByRecipient(Long idRecipient) {
        return messageRepository.findByRecipient(idRecipient);
    }

    public Message findById(Long id) throws NoSuchElementException {
        return messageRepository.findById(id).get();
    }
}
