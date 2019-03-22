package controller;

import command.Command;
import command.exception.CommandException;
import command.factory.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mainWindow")
@MultipartConfig
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("button");
        CommandFactory factory = CommandFactory.getInstance();
        boolean allowed = (boolean) request.getAttribute("allowed");
        if (allowed) {
            try {
                Command command = factory.createCommand(action, request, response);
                String path = command.execute();
                request.getRequestDispatcher(path).forward(request, response);
            } catch (CommandException e) {
                LOGGER.error(e.getMessage());
                response.sendRedirect("error");
            }
        } else {
            String redirectTo = (String) request.getAttribute("redirectTo");
            response.sendRedirect(redirectTo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("button");
        CommandFactory factory = CommandFactory.getInstance();
        boolean allowed = (boolean) request.getAttribute("allowed");
        if (allowed) {
            try {
                Command command = factory.createCommand(action, request, response);
                String path = command.execute();
                response.sendRedirect(path);
            } catch (CommandException e) {
                LOGGER.error(e.getMessage());
                response.sendRedirect("error");
            }
        } else {
            String redirectTo = (String) request.getAttribute("redirectTo");
            response.sendRedirect(redirectTo);
        }
    }
}
