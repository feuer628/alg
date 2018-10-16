package com.wd45.servlet;


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


        httpServletRequest.setAttribute("loadCPUImj", answerWS.getByteToString() );
        httpServletResponse.setContentType("image/jpeg");

        //HttpServletResponse.getWriter().write(imageString.toString());

        httpServletRequest.getRequestDispatcher("/loadCPU.jsp").forward(httpServletRequest, httpServletResponse);


        //httpServletResponse.getWriter().print(String.format("Load CPU : %d", loadCPU));

    }
}