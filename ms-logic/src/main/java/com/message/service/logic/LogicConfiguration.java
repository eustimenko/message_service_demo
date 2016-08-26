package com.message.service.logic;

import com.message.service.domain.DomainConfiguration;
import com.message.service.logic.service.*;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.message.service.logic.service")
@Import({DomainConfiguration.class})
public class LogicConfiguration {

    @Bean
    public UserService userService() {
        return new DefaultUserService();
    }

    @Bean
    public MessageService messageService() {
        return new DefaultMessageService();
    }
}
