package com.liu.activemq.spring.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class QueueConsumer {

    //监听的队列或主题名
    @JmsListener(destination = "queue1")
    public void receiveQueue(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            String info = mapMessage.getString("info");
            String info1 = mapMessage.getString("info1");

            System.out.println(info + info1);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
