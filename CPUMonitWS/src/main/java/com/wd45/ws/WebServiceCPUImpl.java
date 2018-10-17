package com.wd45.ws;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.wd45.rabbitmq.RabbitMQProduser;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Date;
import javax.management.*;
import javax.xml.crypto.Data;

@WebService(endpointInterface = "com.wd45.ws.WebServiceCPU")
public class WebServiceCPUImpl implements WebServiceCPU {

    private final static int IMAGE_WIDTH = 170;
    private final static int IMAGE_HEIGHT = 140;
    private final static int FONT_SIZE = 150;
    private final static int TEXT_COORD_X = 0;
    private final static int TEXT_COORD_Y = 125;
    private final static int CPU_FREQ = 50;

    @Override
    public byte[] getCPULoad() throws Exception {

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
        //SystemCpuLoad - загрузка системы
        //ProcessCpuLoad - загрузка JVM
        AttributeList attrs = server.getAttributes(name, new String[]{"SystemCpuLoad", "ProcessCpuLoad"});
        double cpuLoad = (double)((Attribute)attrs.get(0)).getValue();
        int cpuLoadPercent = (int)(cpuLoad * 100);

        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(new Font("SansSerif", Font.PLAIN, FONT_SIZE));
        graphics.drawString(Integer.toString(cpuLoadPercent), TEXT_COORD_X, TEXT_COORD_Y);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        outputStream.flush();
        String base64String = Base64.encode(outputStream.toByteArray());
        outputStream.close();


        if (cpuLoadPercent >= CPU_FREQ){
            Date date = new Date();
            RabbitMQProduser.setMessage(
                    String.format("CPU frequency : %d, date %s",cpuLoadPercent, date.toString()));
        }

        return  Base64.decode(base64String);
    }

}