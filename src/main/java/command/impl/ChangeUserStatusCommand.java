package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.UserStatus;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeUserStatusCommand implements Command {

    private AdminService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ChangeUserStatusCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Admin.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ChangeUserStatusCommand(AdminService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of changing user's status (admin, movie fan, alien specialist,
     * user). Request provides information about user's ID and new status, then
     * service layer is called to save changes.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
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
