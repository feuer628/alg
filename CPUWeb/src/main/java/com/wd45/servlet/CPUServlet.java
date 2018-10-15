package com.wd45.servlet;

import com.wd45.ws.WebServiceCPU;
import sun.misc.BASE64Encoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;

@WebServlet("/loadCPU")
public class CPUServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {

        URL url = new URL("http://localhost:8081/cpu?wsdl");
        QName qname = new QName("http://ws.wd45.com/", "WebServiceCPUImplService");
        Service service = Service.create(url, qname);
        httpServletResponse.addHeader("Refresh", "1");
        WebServiceCPU webServiceCPU = service.getPort(WebServiceCPU.class);
        byte[] loadCPU = webServiceCPU.getCPULoad();
        

        BASE64Encoder base64Encoder = new BASE64Encoder();
        StringBuilder imageString = new StringBuilder();
        imageString.append("data:image/jpg;base64,");
        imageString.append(base64Encoder.encode(loadCPU));

        httpServletRequest.setAttribute("loadCPUImj", imageString.toString());
        httpServletResponse.setContentType("image/jpeg");

        httpServletRequest.getRequestDispatcher("/loadCPU.jsp").forward(httpServletRequest, httpServletResponse);


        //httpServletResponse.getWriter().print(String.format("Load CPU : %d", loadCPU));

    }
}