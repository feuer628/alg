package com.wd45.servlet;

import com.wd45.ws.WebServiceCPU;

import javax.servlet.http.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;
public class CPUServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        URL url = new URL("http://localhost:8081/cpu?wsdl");
        QName qname = new QName("http://ws.wd45.com/", "WebServiceCPUImplService");
        Service service = Service.create(url, qname);
        httpServletResponse.addHeader("Refresh", "1");
        WebServiceCPU webServiceCPU = service.getPort(WebServiceCPU.class);
        byte[] loadCPU = webServiceCPU.getCPULoad();

        httpServletResponse.setContentType("image/jpeg");
        httpServletResponse.setContentLength(loadCPU.length);
        httpServletResponse.getOutputStream().write(loadCPU);



        //httpServletResponse.getWriter().print(String.format("Load CPU : %d", loadCPU));

    }
}