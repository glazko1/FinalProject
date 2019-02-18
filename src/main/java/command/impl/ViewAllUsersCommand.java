package command.impl;

import command.Command;
import command.exception.CommandException;
import service.exception.ServiceException;
import service.impl.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAllUsersCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewAllUsersCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() throws CommandException {
        Admin admin = Admin.getInstance();
        try {
            admin.viewAllUsers(request, response);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
