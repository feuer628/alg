package com.wd45.rabbitmq;

public final class Message {

    private static String message =null;

    public static String getMessage(){
        String buf = message;
        message = null;
        return buf;
    }

    public static void setMessage( String message){
        Message.message = message;
    }

}