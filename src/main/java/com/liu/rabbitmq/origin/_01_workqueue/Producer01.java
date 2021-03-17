package com.liu.rabbitmq.origin._01_workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
*工作队列生产者（不用交换机）
1、一条消息只会被一个消费者接收；
2、rabbit采用轮询的方式将消息是平均发送给消费者的；
3、消费者在处理完某条消息后，才会收到下一条消息。
生产者操作流程如下：
1）创建连接
2）创建通道
3）声明队列
4）发送消息
*/
public class Producer01 {
    //队列名称
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            connection = factory.newConnection();
            channel = connection.createChannel();

            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建,可以不申明
             * param1:队列名称
             * param2:是否持久化
             * param3:队列是否独占此连接
             * param4:队列不再使用时是否自动删除此队列
             * param5:队列参数
             */
            channel.queueDeclare(QUEUE, true, false, false, null    );
            String message = "helloworld小明"+System.currentTimeMillis();
            /**
             * 消息发布方法
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * param3:消息包含的属性
             * param4：消息体
             */
            /**
             * 这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显示绑定或解除绑定
             *　默认的交换机，routingKey等于队列名称
             */
            channel.basicPublish("", QUEUE, null, message.getBytes());
            System.out.println("Send Message is:'" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(channel != null)
            {
                channel.close();
            }
            if(connection != null)
            {
                connection.close();
            }
        }
    }


}
