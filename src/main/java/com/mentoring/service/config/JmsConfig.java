package com.mentoring.service.config;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
@Configuration
public class JmsConfig {

    @Bean
    public ActiveMQConnectionFactory jmsConnectionFactory() {
        final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616"); // can differ from ActiveMQ server URL
        factory.setTrustedPackages(Arrays.asList("com.mentoring", "java.util"));

        return factory;
    }

    @Bean
    public ObjectMapper modelMapper() {
        return new ObjectMapper();
    }

}
