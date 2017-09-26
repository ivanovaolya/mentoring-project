package com.mentoring.service.jms;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentoring.web.dto.card.CardDto;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
@Service
public class ProcessCardProducer {

    @Autowired
    private ActiveMQConnectionFactory jmsConnectionFactory;

    @Autowired
    private ObjectMapper objectMapper;

    public void submitCardDetails(final CardDto cardDto) throws Exception {
        final Connection connection = jmsConnectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue paymentQueue = session.createQueue("CARD_DETAILS");
        final MessageProducer producer = session.createProducer(paymentQueue);
        final TextMessage message = session.createTextMessage(objectMapper.writeValueAsString(cardDto));
        producer.send(message);

        System.out.println("*** JMS Message sent ***");
        System.out.println(message.getText());
        System.out.println();

        connection.stop();
    }
}
