package com.message.service.domain;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.message.service.domain.repository"})
@PropertySource("classpath:domain.properties")
public class DomainConfiguration {

    @Bean
    DataSource dataSource(Environment env) throws PropertyVetoException {
        final ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getRequiredProperty("database.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("database.url"));
        dataSource.setUser(env.getRequiredProperty("database.username"));
        dataSource.setPassword(env.getRequiredProperty("database.password"));

        dataSource.setInitialPoolSize(5);
        dataSource.setMinPoolSize(5);
        dataSource.setAcquireIncrement(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setMaxStatements(100);

        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", false);

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("com.message.service.domain.entity");
        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);

        return manager;
    }

}
