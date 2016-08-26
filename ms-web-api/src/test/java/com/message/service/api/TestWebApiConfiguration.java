package com.message.service.api;

import com.message.service.auth.services.*;
import com.message.service.domain.DomainConfiguration;
import com.message.service.logic.service.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.*;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan
@Import({DomainConfiguration.class})
public class TestWebApiConfiguration {

    @Bean
    DataSource dataSource() throws PropertyVetoException {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

        return builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/fill-db.sql")
                .build();
    }

    @Bean
    public UserService userServiceMock() {
        return mock(DefaultUserService.class);
    }

    @Bean
    public MessageService messageServiceMock() {
        return mock(DefaultMessageService.class);
    }

    @Bean
    public AuthService authService() {
        return mock(DefaultAuthService.class);
    }
}
