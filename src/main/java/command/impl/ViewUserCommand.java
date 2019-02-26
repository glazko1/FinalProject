package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewUserCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long id = Long.parseLong(request.getParameter("userId"));
        try {
            User user = service.viewUser(id);
            request.setAttribute("user", user);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "user-page";
    }
}