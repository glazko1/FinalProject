package controller;

import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/mainWindow")
public class MainWindowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("button");
        if ("signIn".equals(action)) {
            Common common = Common.getInstance();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                common.signIn(username, password);
                response.sendRedirect("jsp/main.jsp");
            } catch (ServiceException e) {
                response.sendRedirect("jsp/error.jsp");
            }
        }
    }
}
