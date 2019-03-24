package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteNotificationCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public DeleteNotificationCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    DeleteNotificationCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of deleting a notification. Request provides ID of
     * notification, then service layer method is called.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String notificationId = request.getParameter("notificationId");
        try {
            service.deleteNotification(notificationId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
