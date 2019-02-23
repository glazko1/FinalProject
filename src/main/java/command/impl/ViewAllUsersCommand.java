package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllUsersCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewAllUsersCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        AdminService service = Admin.getInstance();
        try {
            List<User> users = service.viewAllUsers();
            request.setAttribute("users", users);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "user-table";
    }
}
