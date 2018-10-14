package com.wd45.client;
import com.wd45.ws.WebServiceCPU;

import javax.imageio.ImageIO;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.xml.namespace.QName;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.xml.ws.Service;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WebServiceCPUClient {

    public static void main (String arg[])
            throws IOException, MalformedObjectNameException,
            InstanceNotFoundException, ReflectionException, InterruptedException {
        URL url = new URL("http://localhost:8081/cpu?wsdl");
        QName qname = new QName("http://ws.wd45.com/", "WebServiceCPUImplService");
        Service service = Service.create(url, qname);

        WebServiceCPU webServiceCPU = service.getPort(WebServiceCPU.class);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println(webServiceCPU.getCPULoad().toString());
            } catch (MalformedObjectNameException e) {

            } catch (IOException e) {

            } catch (InstanceNotFoundException e) {

            } catch (ReflectionException e) {

            }

        }, 0, 1, SECONDS);

    }
}