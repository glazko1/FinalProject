package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Notification;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewNotificationsCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewNotificationsCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewNotificationsCommand(CommonService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of getting a user's notifications. User's ID is provided by
     * request, then service layer returns all notifications and they're put into
     * request to show on page.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        long userId = Long.parseLong(request.getParameter("userId"));
        if (!checker.checkAccess(userId, request)) {
            return "main";
        }
        try {
            List<Notification> notifications = service.viewNotifications(userId);
            request.setAttribute("notifications", notifications);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "notifications";
    }
}
