package com.message.service.api.controller;

import com.message.service.api.*;
import com.message.service.auth.AuthConfiguration;
import com.message.service.domain.entity.User;
import com.message.service.logic.service.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApiConfiguration.class, AuthConfiguration.class, TestWebApiConfiguration.class})
@WebAppConfiguration
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(formLogin("/auth/login")
                .user("login", "admin")
                .password("password", "admin"))
                .andDo(print())
                .andExpect(authenticated().withRoles("ROOT"));
    }

    @Test
    public void login_invalid() throws Exception {
        mockMvc.perform(formLogin("/auth/login")
                .user("login", "admin")
                .password("password", "password"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @Test
    public void logout_test() throws Exception {
        mockMvc.perform(logout())
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void register() throws Exception {
        //arrange
        final User params = new User();
        params.setEmail("test@test.com");
        params.setLogin("test");
        params.setPassword("password");
        params.setConfirmPassword("password");

        final GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        //act
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(converter.getGson().toJson(params, params.getClass())))
                .andDo(print())
                .andExpect(status().isOk());

        //assert
        userServiceMock.findByLogin("test");
        verify(userServiceMock, times(1)).findByLogin("test");
    }
}
