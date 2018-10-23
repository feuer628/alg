package com.wd45.servlet;
import com.google.common.collect.Iterables;
import com.wd45.rabbitmq.RabbitMQConsumer;
import com.wd45.ws.AnswerWS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loadCPU")
public class CPUServlet extends HttpServlet {

    private List<RabbitMQConsumer> rabbitMQConsumers = new ArrayList<>();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {

        AnswerWS answerWS = new AnswerWS();

        String qName = httpServletRequest.getParameter("qname");
        RabbitMQConsumer rabbitMQConsumer = new RabbitMQConsumer(qName);
        RabbitMQConsumer consumer = rabbitMQConsumers.stream()
                .filter((x) -> x.QNAME == qName)
                .findFirst()
                .orElse(null);

        if(consumer == null) {
            rabbitMQConsumers.add(rabbitMQConsumer);
        }

        String action = httpServletRequest.getParameter("action");

        if(action == null ){
            httpServletRequest.setAttribute("loadCPUImj", answerWS.getBytesToString());
            httpServletResponse.setContentType("image/jpeg");
            httpServletRequest.getRequestDispatcher("/loadCPU.jsp").forward(httpServletRequest, httpServletResponse);
        }
        else if(action.equals("refresh")){
            httpServletResponse.getWriter().write(answerWS.getBytesToString());
        }
        else if(action.equals("answerWS")){

            for (RabbitMQConsumer consumer1: rabbitMQConsumers) {
                try {
                    if (consumer1.getMessages() != null && consumer1.QNAME.equals(qName)) {
                        httpServletResponse.getWriter().write(consumer1.getMessages());
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
