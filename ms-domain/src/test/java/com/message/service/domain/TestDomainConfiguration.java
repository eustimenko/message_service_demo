package com.message.service.domain;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:domain.properties")
@ComponentScan
@Import({DomainConfiguration.class})
public class TestDomainConfiguration {

    @Bean(destroyMethod = "close")
    DataSource dataSource(Environment env) throws PropertyVetoException {
        final ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getRequiredProperty("database.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("database.url"));
        dataSource.setUser(env.getRequiredProperty("database.username"));
        dataSource.setPassword(env.getRequiredProperty("database.password"));

        return dataSource;
    }

}
