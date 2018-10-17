package com.wd45.rabbitmq;
import com.rabbitmq.client.Channel;


public class MessageThread extends Thread {

    private Channel channel;
    private String message;
    private long tag;


    public MessageThread(Channel channel, String message, long tag) {
        this.channel = channel;
        this.message = message;
        this.tag = tag;

    }

    @Override
    public void run() {
        try {

            Message.setMessage(message);

            channel.basicAck(tag, true); // удаление сообщения
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}