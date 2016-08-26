package com.message.service.domain.helper;

import com.message.service.domain.entity.*;
import com.message.service.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestHelper {

    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_LOGIN = "test_user";
    public static final String DEFAULT_MESSAGE_TITLE = "test title";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public User prepareUser() {
        final User user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword("Test_Password");
        user.setEmail("test@test.com");
        user.setFullname("test test test");

        return userRepository.save(user);
    }

    public Message prepareMessage() {
        final Message message = new Message();
        message.setId(DEFAULT_ID);
        message.setTitle(DEFAULT_MESSAGE_TITLE);
        message.setText("This is test message text");
        final User user = prepareUser();
        message.setAuthor(user);
        message.setRecipient(user);

        return messageRepository.save(message);
    }
}
