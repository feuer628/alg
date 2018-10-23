package com.wd45.servlet;
import com.google.common.collect.Iterables;
import com.wd45.rabbitmq.RabbitMQConsumer;
import com.wd45.ws.AnswerWS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loadCPU")
public class CPUServlet extends HttpServlet {

    private static Set<RabbitMQConsumer> rabbitMQConsumers = new HashSet<>();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {

        AnswerWS answerWS = new AnswerWS();

        String qName = httpServletRequest.getParameter("qname");

        RabbitMQConsumer consumer = rabbitMQConsumers.stream()
                .filter((x) -> x.QNAME == qName)
                .findFirst()
                .orElse(null);

        if(consumer == null) {
            RabbitMQConsumer rabbitMQConsumer = new RabbitMQConsumer(qName);
            Thread thread = new Thread(rabbitMQConsumer);
            thread.setDaemon(true);
            thread.start();
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
                    if (consumer1.QNAME.equals(qName)) {
                        httpServletResponse.getWriter().write(Iterables.getLast(consumer1.message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
