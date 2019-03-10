package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import entity.UserStatus;
import service.CommonService;
import service.exception.user.BannedUserException;
import service.exception.user.InvalidUserInformationException;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SignInCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public SignInCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            User user = service.signIn(username, password);
            session.setAttribute("userId", String.valueOf(user.getUserId()));
            session.setAttribute("username", user.getUsername());
            UserStatus status = user.getStatus();
            session.setAttribute("status", String.valueOf(status.getStatusId()));
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
        } catch (BannedUserException e) {
            session.setAttribute("signInMessage", "message.user_is_banned");
            return "index";
        } catch (InvalidUserInformationException e) {
            session.setAttribute("signInMessage", "message.invalid_username_password");
            return "index";
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
