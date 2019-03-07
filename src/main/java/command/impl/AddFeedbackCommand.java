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

    public AddFeedbackCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public AddFeedbackCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long alienId = Long.parseLong(request.getParameter("alienId"));
        String username = request.getParameter("username");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String feedbackText = request.getParameter("feedbackText");
        try {
            service.addFeedback(alienId, username, rating, feedbackText);
            service.recountAverageRating(alienId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
