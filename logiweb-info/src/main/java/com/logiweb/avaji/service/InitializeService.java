package com.logiweb.avaji.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSPasswordCredential;
import javax.jms.Queue;

@Stateless
@LocalBean
public class InitializeService {

    private static final Logger logger = LogManager.getLogger(InitializeService.class);

    @Inject
    @JMSConnectionFactory("java:/RemoteJmsXA")
    @JMSPasswordCredential(userName = "amq", password = "amq")
    private JMSContext context;

    @Resource(lookup = "java:global/remoteContext/initQueue")
    private Queue queue;

    public void init() {
        context.createProducer().send(queue, "START");

        logger.info("Send init message");
    }
}
