package com.wd45.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.ArrayList;


public final class RabbitMQConsumer {
    public static void main (String... arg) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();
        String exchangeName = "exchangeName";
        String queueName = "queueName";
        String routingKey = "routeKey";

        boolean durable = true;
        channel.exchangeDeclare(exchangeName, "direct", durable);
        channel.queueDeclare(queueName, durable,false,false,null);
        channel.queueBind(queueName, exchangeName, routingKey);
        boolean noAck = false;
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, noAck, consumer);
        boolean runInfinite = true;
        while (runInfinite) {
            QueueingConsumer.Delivery delivery;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException ie) {
                continue;
            }
            System.out.println("Message received"
                    + new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        channel.close();
        conn.close();
    }
}