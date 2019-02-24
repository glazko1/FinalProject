package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToEditUserCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToEditUserCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            User user = service.viewUser(userId);
            request.setAttribute("username", user.getUsername());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("email", user.getEmail());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "edit-user";
    }
}
