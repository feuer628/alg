package com.wd45.rabbitmq;

public final class Message {

    private static String message ="";

    public static String getMessage(){
        String buf = message;
        message = "";
        return buf;
    }

    public static void setMessage( String message){
        Message.message = message;
    }

}