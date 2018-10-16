package com.wd45.ws;

import sun.misc.BASE64Encoder;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class AnswerWS {

    private final  String SPEC = "http://localhost:8081/cpu?wsdl";
    private final  String NAME_SPACE_URI = "http://ws.wd45.com/";
    private final  String LOCAL_PORT= "WebServiceCPUImplService";
    private static WebServiceCPU webServiceCPU = null;

    {

        try {
            URL url = new URL(SPEC);
            QName qname = new QName(NAME_SPACE_URI, LOCAL_PORT);
            Service service = Service.create(url, qname);
            webServiceCPU = service.getPort(WebServiceCPU.class);

        } catch (MalformedURLException e) {

        }
    }

    public  byte[] getBytes() throws MalformedURLException {
        return  webServiceCPU.getCPULoad();
    }

    public  String getByteToString() throws MalformedURLException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        StringBuilder imageString = new StringBuilder();
        imageString.append("data:image/jpg;base64,");
        imageString.append(base64Encoder.encode(webServiceCPU.getCPULoad()));

        return imageString.toString();
    }
}