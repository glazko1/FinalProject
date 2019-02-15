package controller;

import command.Command;
import command.exception.CommandException;
import command.factory.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/mainWindow")
public class MainWindowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("button");
        CommandFactory factory = CommandFactory.getInstance();
        Command command;
        switch (action) {
            case "viewAliens":
                try {
                    command = factory.createCommand("viewAliens", request, response);
                    command.execute();
                    HttpSession session = request.getSession();
                    session.setAttribute("aliens", request.getAttribute("aliens"));
                    response.sendRedirect("alien-table");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            case "newAlien":
                HttpSession session = request.getSession();
                session.removeAttribute("aliens");
                try {
                    command = factory.createCommand("viewMovies", request, response);
                    command.execute();
                    session.setAttribute("movies", request.getAttribute("movies"));
                    response.sendRedirect("new-alien");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("button"); // servlet -> command -> service -> dao
        CommandFactory factory = CommandFactory.getInstance();
        Command command;
        switch (action) {
            case "signIn":
                try {
                    command = factory.createCommand("signIn", request, response);
                    command.execute();
                    HttpSession session = request.getSession();
                    session.setAttribute("username", response.getHeader("username"));
                    session.setAttribute("status", response.getHeader("status"));
                    session.setAttribute("firstName", response.getHeader("firstName"));
                    session.setAttribute("lastName", response.getHeader("lastName"));
                    response.sendRedirect("main");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            case "signUp":
                try {
                    command = factory.createCommand("signUp", request, response);
                    command.execute();
                    response.sendRedirect("main");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            case "addAlien":
                try {
                    command = factory.createCommand("addAlien", request, response);
                    command.execute();
                    response.sendRedirect("main");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            default:
                break;
        }
    }
}
