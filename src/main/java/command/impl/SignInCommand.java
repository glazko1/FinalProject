package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SignInCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user = service.signIn(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("userId", String.valueOf(user.getUserId()));
            session.setAttribute("username", user.getUsername());
            session.setAttribute("status", String.valueOf(user.getStatusId()));
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
