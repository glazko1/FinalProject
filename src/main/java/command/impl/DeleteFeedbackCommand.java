package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFeedbackCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public DeleteFeedbackCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
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
