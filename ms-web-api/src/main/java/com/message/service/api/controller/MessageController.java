package com.message.service.api.controller;

import com.message.service.domain.entity.Message;
import com.message.service.logic.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET, value = "by/{idUser}")
    public List<Message> viewUserMessages(@PathVariable("idUser") Long idUser) {
        return messageService.findByRecipient(idUser);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{idMessage}")
    public void deleteMessage(@PathVariable("idMessage") Long idMessage) throws NoSuchElementException {
        messageService.delete(idMessage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{idMessage}")
    public Message viewMessage(@PathVariable("idMessage") Long idMessage) throws NoSuchElementException {
        return messageService.findById(idMessage);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void sendMessage(@RequestBody Message message) throws NoSuchElementException {
        messageService.create(message);
    }
}
