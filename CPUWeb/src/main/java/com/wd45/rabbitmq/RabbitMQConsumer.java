package com.wd45.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

import java.util.concurrent.TimeoutException;


public  class RabbitMQConsumer  implements Runnable{

    public  void run ()  {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);

        Connection conn = null;
        try {
            conn = factory.newConnection();
        } catch (Exception ex) {return;}

        String exchangeName = "exchangeName";
        String queueName = "queueName";
        String routingKey = "routeKey";

        Channel channel = null;
        QueueingConsumer consumer = null;
        boolean durable = true;
        try {
            channel = conn.createChannel();
            channel.exchangeDeclare(exchangeName, "direct", durable);
            channel.queueDeclare(queueName, durable,false,false,null);
            channel.queueBind(queueName, exchangeName, routingKey);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {

        }

        boolean runInfinite = true;
        while (runInfinite) {
            QueueingConsumer.Delivery delivery ;
            try {
                delivery = consumer.nextDelivery();
                Message.setMessage(new String(delivery.getBody()));
            } catch (InterruptedException ie) {
                break;
            }
        }
        try {
            channel.close();
            conn.close();
        } catch (IOException e) {

        } catch (TimeoutException e) {

        }
    }
}