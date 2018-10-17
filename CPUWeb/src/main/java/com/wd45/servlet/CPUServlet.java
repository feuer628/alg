package com.wd45.servlet;
import com.wd45.rabbitmq.Message;
import com.wd45.rabbitmq.RabbitMQConsumer;
import com.wd45.ws.AnswerWS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loadCPU")
public class CPUServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {

        AnswerWS answerWS = new AnswerWS();

        RabbitMQConsumer rabbitMQConsumer = new RabbitMQConsumer();
        Thread thread = new Thread(rabbitMQConsumer);
        thread.setDaemon(true);
        thread.start();

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
            httpServletResponse.getWriter().write(Message.getMessage());
        }
    }
}
