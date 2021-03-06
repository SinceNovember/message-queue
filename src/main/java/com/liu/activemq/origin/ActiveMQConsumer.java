package com.liu.activemq.origin;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/*
*
使用MessageConsumer完成消费
1. 创建链接工厂
2. 创建链接
3. 启动链接
4. 获取会话
5. 创建队列
6. 创建消费者
7. 消费消息
8. 提交
9. 关闭资源
* */
public class ActiveMQConsumer {
    public static void main(String[] args) throws JMSException {
        // 1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        // 2.创建连接
        Connection connection = connectionFactory.createConnection();
        // 3.开启连接
        connection.start();
        // 4.创建会话
        /*
         *  第一个参数,是否使用事务
         * 如果设置true,操作消息队列后, 必须使用 session.commit();
         * 如果设置false, 操作消息队列后, 不使用 session.commit();
         */
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 5.创建队列
        Queue queue = session.createQueue("hello3");
        //主题使用topic即可
        //Topic queue = session.createTopic("hello3");
        // 6.创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        while (true) {
            TextMessage message = (TextMessage) consumer.receive(10000);
            if (message!=null) {
                System.out.println(message.getText());
            }else{
                break;
            }
        }
        // 7.关闭连接
        session.commit();
        session.close();
        connection.close();
        System.out.println("消费结束");
    }
}
