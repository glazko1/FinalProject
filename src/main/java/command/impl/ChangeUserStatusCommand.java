package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeUserStatusCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ChangeUserStatusCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        AdminService service = Admin.getInstance();
        long userId = Long.parseLong(request.getParameter("userId"));
        int statusId = Integer.parseInt(request.getParameter("status"));
        try {
            service.changeUserStatus(userId, statusId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
