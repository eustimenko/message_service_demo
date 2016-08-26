package com.message.service.api;

import com.message.service.api.auth.AuthFilter;
import com.message.service.auth.AuthConfiguration;
import com.message.service.domain.DomainConfiguration;
import com.message.service.logic.LogicConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class ServletDispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                WebApiConfiguration.class,
                DomainConfiguration.class,
                LogicConfiguration.class,
                SecurityConfiguration.class,
                AuthConfiguration.class
        };
    }

    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    protected String[] getServletMappings() {
        return new String[]{"/api"};
    }

    protected Filter[] getServletFilters() {
        return new Filter[]{
                new AuthFilter()
        };
    }
}
