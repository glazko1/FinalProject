package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddFeedbackCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public AddFeedbackCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    AddFeedbackCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of adding a new feedback about some alien. Request provides
     * information about alien's ID, user's login, given rating to alien and user's
     * comment. Then service methods are called to add feedback and recount alien's
     * average rating.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String alienId = request.getParameter("alienId");
        String userId = request.getParameter("userId");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String feedbackText = request.getParameter("feedbackText");
        try {
            service.addFeedback(alienId, userId, rating, feedbackText);
            service.recountAverageRating(alienId);
            service.reviewUserStatus(userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
