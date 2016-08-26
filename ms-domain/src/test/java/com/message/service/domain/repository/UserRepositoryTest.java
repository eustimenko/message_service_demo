package com.message.service.domain.repository;

import com.google.common.collect.Sets;
import com.message.service.domain.TestDomainConfiguration;
import com.message.service.domain.entity.User;
import com.message.service.domain.helper.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static com.message.service.domain.helper.TestHelper.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDomainConfiguration.class})
@Transactional
@Rollback
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestHelper helper;

    @Test
    public void saveAndGetById() {
        //arrange
        final User user = helper.prepareUser();

        //act
        final Optional<User> result = repository.findById(user.getId());

        //assert
        assertTrue(result.isPresent());
        assertEquals(DEFAULT_LOGIN, result.get().getLogin());
    }

    @Test
    public void update() {
        //arrange
        final User user = helper.prepareUser();
        final String newLogin = "newUser";
        user.setLogin(newLogin);

        //act
        final Optional<User> result = repository.findById(user.getId());

        //assert
        assertTrue(result.isPresent());
        assertEquals(newLogin, result.get().getLogin());
    }

    @Test
    public void delete() {
        //arrange
        final User user = helper.prepareUser();

        //act
        repository.delete(user);

        //assert
        assertFalse(repository.findById(user.getId()).isPresent());
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateEmail_error() {
        //arrange
        final User user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setEmail("invalid_email_format");
        user.setFullname("test test test");
        user.setPassword("Test_Password");

        //act
        repository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void validatePassword_error() {
        //arrange
        final User user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setEmail("invalid_email_format");
        user.setFullname("test test test");
        user.setPassword("without_upper_case");

        //act
        repository.save(user);
    }

    @Test
    public void findPossibleFriends() {
        //arrange
        final User user1 = helper.prepareUser();

        User user2 = new User();
        user2.setId(2L);
        user2.setLogin("test_user2");
        user2.setEmail("test_user2@test.test");
        user2.setFullname("test test test");
        user2.setPassword("Test_password");
        user2.replaceFriends(Sets.newHashSet(user1));
        user2 = repository.save(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setLogin("test_user3");
        user3.setEmail("test_user3@test.test");
        user3.setFullname("test test test");
        user3.setPassword("Test_password");
        user3 = repository.save(user3);

        //act
        final List<User> friends = repository.findPossibleFriends(user2.getId());

        //assert
        assertFalse(friends.isEmpty());
        assertTrue(friends.size() == 1);
        assertEquals(user3.getLogin(), friends.get(0).getLogin());
    }

    @Test
    public void findByLogin() {
        //arrange
        helper.prepareUser();

        //act
        final Optional<User> user = repository.findByLogin(TestHelper.DEFAULT_LOGIN);

        //assert
        assertTrue(user.isPresent());
        assertEquals(TestHelper.DEFAULT_LOGIN, user.get().getLogin());
    }
}
