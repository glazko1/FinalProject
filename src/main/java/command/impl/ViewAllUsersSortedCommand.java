package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import entity.UserStatus;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllUsersSortedCommand implements Command {

    private AdminService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewAllUsersSortedCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Admin.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewAllUsersSortedCommand(AdminService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of getting information about all users sorted by parameter
     * and type, provided by request. List of users is provided by service layer,
     * then it's put into request with sorting parameters.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        String sortedBy = request.getParameter("sortedBy");
        String sortType = request.getParameter("sortType");
        try {
            List<User> users = service.viewAllUsersSorted(sortedBy, sortType);
            request.setAttribute("users", users);
            request.setAttribute("sortedBy", sortedBy);
            request.setAttribute("sortType", sortType);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "user-table";
    }
}
