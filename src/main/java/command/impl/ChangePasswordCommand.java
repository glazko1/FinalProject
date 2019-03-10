package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.user.InvalidPasswordException;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ChangePasswordCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public ChangePasswordCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long userId = Long.parseLong(request.getParameter("userId"));
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        HttpSession session = request.getSession();
        try {
            service.changePassword(userId, currentPassword, newPassword, confirmedPassword);
        } catch (InvalidPasswordException e) {
            session.setAttribute("message", "message.invalid_password");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
