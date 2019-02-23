package controller;

import command.Command;
import command.exception.CommandException;
import command.factory.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mainWindow")
public class MainWindowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("button");
        CommandFactory factory = CommandFactory.getInstance();
        try {
            Command command = factory.createCommand(action, request, response);
            String path = command.execute();
            request.getRequestDispatcher(path).forward(request, response);
        } catch (CommandException e) {
            response.sendRedirect("error");
        }
//        switch (action) {
//            case "viewAllAliens":
//                try {
//                    command = factory.createCommand("viewAllAliens", request, response);
//                    command.execute();
//                    request.getRequestDispatcher("alien-table").forward(request, response);
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "forwardToNewAlien":
//                try {
//                    command = factory.createCommand("forwardToNewAlien", request, response);
//                    String path = command.execute();
//                    request.getRequestDispatcher(path).forward(request, response);
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "viewAllMovies":
//                break;
//            case "viewAllUsers":
//                try {
//                    command = factory.createCommand("viewAllUsers", request, response);
//                    String path = command.execute();
//                    request.getRequestDispatcher(path).forward(request, response);
////                    session.setAttribute("users", request.getAttribute("users"));
////                    response.sendRedirect("user-table");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "viewAlien":
//                try {
//                    command = factory.createCommand("viewAlien", request, response);
//                    String path = command.execute();
//                    request.getRequestDispatcher(path).forward(request, response);
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//            case "viewUser":
//                try {
//                    command = factory.createCommand("viewUser", request, response);
//                    String path = command.execute();
//                    request.getRequestDispatcher(path).forward(request, response);
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//            case "viewMovie":
//                break;
//            default:
//                break;
//        }
//        if (action.matches("viewAlien[0-9]+")) {
//            String id = action.substring(9);
//            request.setAttribute("id", id);
//            try {
//                command = factory.createCommand("viewAlien", request, response);
//                command.execute();
//                session.setAttribute("alien", request.getAttribute("alien"));
//                session.setAttribute("feedbacks", request.getAttribute("feedbacks"));
//                response.sendRedirect("alien-page");
//            } catch (CommandException e) {
//                response.sendRedirect("error");
//            }
//        } else
//        if (action.matches("viewMovie[0-9]+")) {
//            String id = action.substring(9);
//            request.setAttribute("id", id);
//            try {
//                command = factory.createCommand("viewMovie", request, response);
//                command.execute();
//                session.setAttribute("movie", request.getAttribute("movie"));
//                response.sendRedirect("movie-page");
//            } catch (CommandException e) {
//                response.sendRedirect("error");
//            }
//        }
//        } else if (action.matches("viewUser[0-9]+")) {
//            String id = action.substring(8);
//            request.setAttribute("id", id);
//            try {
//                command = factory.createCommand("viewUser", request, response);
//                command.execute();
//                session.setAttribute("user", request.getAttribute("user"));
//                response.sendRedirect("user-page");
//            } catch (CommandException e) {
//                response.sendRedirect("error");
//            }
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("button"); // servlet -> command -> service -> dao
        CommandFactory factory = CommandFactory.getInstance();
        try {
            Command command = factory.createCommand(action, request, response);
            String path = command.execute();
            response.sendRedirect(path);
        } catch (CommandException e) {
            response.sendRedirect("error");
        }
//        switch (action) {
//            case "signIn":
//                try {
//                    command = factory.createCommand("signIn", request, response);
//                    command.execute();
//                    response.sendRedirect("main");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "signUp":
//                try {
//                    command = factory.createCommand("signUp", request, response);
//                    command.execute();
//                    response.sendRedirect("index");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "addAlien":
//                try {
//                    command = factory.createCommand("addAlien", request, response);
//                    command.execute();
//                    response.sendRedirect("main");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "addFeedback":
//                try {
//                    command = factory.createCommand("addFeedback", request, response);
//                    command.execute();
//                    response.sendRedirect("main");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//            case "changeBanStatus":
//                try {
//                    command = factory.createCommand("changeBanStatus", request, response);
//                    command.execute();
//                    response.sendRedirect("main");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            case "changeUserStatus":
//                try {
//                    command = factory.createCommand("changeUserStatus", request, response);
//                    command.execute();
//                    response.sendRedirect("main");
//                } catch (CommandException e) {
//                    response.sendRedirect("error");
//                }
//                break;
//            default:
//                break;
//        }
    }
}
