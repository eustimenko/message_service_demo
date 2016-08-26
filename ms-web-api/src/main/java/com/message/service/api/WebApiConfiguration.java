package com.message.service.api;

import com.message.service.logic.LogicConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan
@Import({LogicConfiguration.class})
public class WebApiConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public View jsonTemplate() {
        final MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true);
        builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return builder;
    }

    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
