package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFeedbackCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public DeleteFeedbackCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    DeleteFeedbackCommand(CommonService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of deleting a feedback. Request provides alien's ID, then
     * service layer methods are called: to delete feedback and recount alien's
     * average rating.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        long userId = Long.parseLong(request.getParameter("userId"));
        if (!checker.checkAccess(userId, request)) {
            return "main";
        }
        long feedbackId = Long.parseLong(request.getParameter("feedbackId"));
        long alienId = Long.parseLong(request.getParameter("alienId"));
        try {
            service.deleteFeedback(feedbackId);
            service.recountAverageRating(alienId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
