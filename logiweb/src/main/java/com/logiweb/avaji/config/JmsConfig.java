package com.logiweb.avaji.config;

import com.logiweb.avaji.service.implementetions.mq.InitializeListener;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.JMSException;

@Configuration
@EnableJms
@PropertySource({"classpath:application.properties"})
public class JmsConfig {

    @Autowired
    private Environment environment;
    @Autowired
    private InitializeListener listener;


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(environment.getProperty("activemq.url"));
        return factory;
    }


    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory());
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setMessageConverter(messageConverter());
        return jmsTemplate;
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer() throws JMSException {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(activeMQConnectionFactory());
        messageListenerContainer.setupMessageListener(listener);
        messageListenerContainer.setDestinationName("initQueue");
        return messageListenerContainer;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
