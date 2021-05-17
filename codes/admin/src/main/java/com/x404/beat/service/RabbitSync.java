package com.x404.beat.service;

import com.x404.beat.core.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chaox on 6/6/2017.
 */
@Service("rabbitSync")
public class RabbitSync
{

    private final static Logger logger = LoggerFactory.getLogger(RabbitSync.class);

    public static String systemId = UUIDUtils.next();

    @Autowired
    private AmqpTemplate amqpTemplate;





    public void sendSyncMessage(){
        MessageProperties properties = new MessageProperties();
        properties.setHeader("systemId", systemId);
        properties.setHeader("event", "com.x404.beat.event.ConfigEvent");
        Message message = new Message("configEvent".getBytes(), properties);
        amqpTemplate.send(message);
    }


}
