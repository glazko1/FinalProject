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
        HttpSession session = request.getSession();
        CommandFactory factory = CommandFactory.getInstance();
        Command command;
        switch (action) {
            case "viewAllAliens":
                try {
                    command = factory.createCommand("viewAllAliens", request, response);
                    command.execute();
                    session.setAttribute("aliens", request.getAttribute("aliens"));
                    response.sendRedirect("alien-table");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            case "newAlien":
                session.removeAttribute("aliens");
                try {
                    command = factory.createCommand("viewAllMovies", request, response);
                    command.execute();
                    session.setAttribute("movies", request.getAttribute("movies"));
                    response.sendRedirect("new-alien");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            case "viewAllMovies":
                break;
            case "viewAllUsers":
                try {
                    command = factory.createCommand("viewAllUsers", request, response);
                    command.execute();
                    session.setAttribute("users", request.getAttribute("users"));
                    response.sendRedirect("user-table");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
                break;
            default:
                break;
        }
        if (action.matches("viewAlien[0-9]+")) {
            String id = action.substring(9);
            request.setAttribute("id", id);
            try {
                command = factory.createCommand("viewAlien", request, response);
                command.execute();
                session.setAttribute("alien", request.getAttribute("alien"));
                session.setAttribute("feedbacks", request.getAttribute("feedbacks"));
                response.sendRedirect("alien-page");
            } catch (CommandException e) {
                response.sendRedirect("error");
            }
        } else if (action.matches("viewMovie[0-9]+")) {
            String id = action.substring(9);
            request.setAttribute("id", id);
            try {
                command = factory.createCommand("viewMovie", request, response);
                command.execute();
                session.setAttribute("movie", request.getAttribute("movie"));
                response.sendRedirect("movie-page");
            } catch (CommandException e) {
                response.sendRedirect("error");
            }
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
                    response.sendRedirect("index");
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
            case "addFeedback":
                try {
                    command = factory.createCommand("addFeedback", request, response);
                    command.execute();
                    response.sendRedirect("main");
                } catch (CommandException e) {
                    response.sendRedirect("error");
                }
            default:
                break;
        }
    }
}
