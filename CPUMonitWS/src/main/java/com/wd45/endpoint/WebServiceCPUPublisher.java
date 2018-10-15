package com.wd45.endpoint;

import com.wd45.ws.WebServiceCPUImpl;
import javax.xml.ws.Endpoint;

public class WebServiceCPUPublisher {

    public static void main (String arg[]) throws Exception {
        Endpoint.publish("http://localhost:8081/cpu", new WebServiceCPUImpl());
    }
}