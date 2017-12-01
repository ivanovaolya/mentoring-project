package com.mentoring.web.config;

import com.mentoring.web.converter.Converter;
import com.mentoring.web.converter.UserConverter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
@Configuration
public class WebRestConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Converter userConverter() {
        return new UserConverter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/static/")
                .addResourceLocations("/resources/js/");
    }
}
