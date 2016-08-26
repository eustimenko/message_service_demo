package com.message.service.api.controller;

import com.message.service.api.*;
import com.message.service.auth.AuthConfiguration;
import com.message.service.domain.entity.*;
import com.message.service.logic.service.MessageService;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApiConfiguration.class, AuthConfiguration.class, TestWebApiConfiguration.class})
@WebAppConfiguration
public class MessageControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageService messageServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void viewUserMessages() throws Exception {
        mockMvc.perform(get("/api/message/by/{idUser}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMessages() throws Exception {
        mockMvc.perform(delete("/api/message/{idMessage}", 3))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void viewMessage() throws Exception {
        mockMvc.perform(get("/api/message/{idMessage}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void sendMessage() throws Exception {
        //arrange
        final Message message = new Message();
        message.setId(4L);
        message.setRecipient(new User());
        message.setTitle("title");
        message.setText("text");

        final GsonHttpMessageConverter converter = new GsonHttpMessageConverter();

        //act
        mockMvc.perform(post("/api/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(converter.getGson().toJson(message, message.getClass())))
                .andDo(print())
                .andExpect(status().isOk());

        //assert
        messageServiceMock.findById(4L);

    }
}
