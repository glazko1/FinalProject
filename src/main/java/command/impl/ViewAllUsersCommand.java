package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.AdminService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllUsersCommand implements Command {

    private AdminService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewAllUsersCommand(AdminService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        try {
            List<User> users = service.viewAllUsers();
            request.setAttribute("users", users);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "user-table";
    }
}
