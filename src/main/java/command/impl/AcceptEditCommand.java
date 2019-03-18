package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.UserStatus;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptEditCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public AcceptEditCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    AcceptEditCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of accepting suggested edit by user. Request provides
     * information about edit's and user's ID, then service methods are called
     * to accept edit, send notification to user and delete edit.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.ALIEN_SPECIALIST, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        long editId = Long.parseLong(request.getParameter("editId"));
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            service.acceptEdit(editId);
            String notificationText = "Your suggested edit #" + editId + " has " +
                    "been accepted! Thank you for your participation in our development!";
            service.sendNotification(userId, notificationText);
            service.deleteEdit(editId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
