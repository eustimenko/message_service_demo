package com.message.service.domain.repository;

import com.message.service.domain.TestDomainConfiguration;
import com.message.service.domain.entity.Message;
import com.message.service.domain.helper.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDomainConfiguration.class})
@Transactional
@Rollback
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private TestHelper helper;

    @Test
    public void saveAndGetById() {
        //arrange
        final Message message = helper.prepareMessage();

        //act
        final Optional<Message> result = repository.findById(message.getId());

        //assert
        assertTrue(result.isPresent());
        assertEquals(TestHelper.DEFAULT_MESSAGE_TITLE, result.get().getTitle());
    }

    @Test
    public void update() {
        //arrange
        final Message message = helper.prepareMessage();
        final String newTitle = "changed test title";
        message.setTitle(newTitle);
        repository.save(message);

        //act
        final Optional<Message> result = repository.findById(message.getId());

        //assert
        assertTrue(result.isPresent());
        assertEquals(newTitle, result.get().getTitle());
    }

    @Test
    public void delete() {
        //arrange
        final Message message = helper.prepareMessage();

        //act
        repository.delete(message);

        //assert
        assertFalse(repository.findById(message.getId()).isPresent());
    }

    @Test
    public void findByAuthor() {
        //arrange
        final Message message = helper.prepareMessage();

        //act
        final List<Message> messages = repository.findByAuthor(message.getAuthor().getId());

        //assert
        assertFalse(messages.isEmpty());
    }

    @Test
    public void findByRecipient() {
        //arrange
        final Message message = helper.prepareMessage();

        //act
        final List<Message> messages = repository.findByRecipient(message.getRecipient().getId());

        //assert
        assertFalse(messages.isEmpty());
    }
}
