package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Notification;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewNotificationsCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewNotificationsCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public ViewNotificationsCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            List<Notification> notifications = service.viewNotifications(userId);
            request.setAttribute("notifications", notifications);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "notifications";
    }
}
