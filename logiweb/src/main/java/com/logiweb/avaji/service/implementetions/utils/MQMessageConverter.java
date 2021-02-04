package com.logiweb.avaji.service.implementetions.utils;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Collections;

public class MQMessageConverter {

    private MappingJackson2MessageConverter messageConverter;

    public MQMessageConverter(Object convertedObject) {
        messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("content-type");
        messageConverter.setTypeIdMappings(Collections.singletonMap("info", convertedObject.getClass()));
    }


    public void convertObject() {

    }
}
