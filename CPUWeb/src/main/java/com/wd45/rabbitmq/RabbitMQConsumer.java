package com.wd45.rabbitmq;

import com.google.common.collect.Iterables;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public  class RabbitMQConsumer {

    public final String QNAME;

    public RabbitMQConsumer(String queueName){
        QNAME = queueName;
    }

    public  String getMessages () throws Exception  {

        String result = "";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);

        Connection conn ;
        try {
            conn = factory.newConnection();
        } catch (Exception ex) {return null;}

        String exchangeName = "exchangeName";
        String queueName = QNAME;
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
            QueueingConsumer.Delivery delivery;
            try {
                delivery = consumer.nextDelivery(100);
            } catch (InterruptedException ie) {
                continue;
            }

            if (delivery == null){
                break;
            }

            result = new String(delivery.getBody());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);

        }

        try {
            channel.close();
            conn.close();
        } catch (IOException e) {

        } catch (TimeoutException e) {

        }
        return result;
    }
}