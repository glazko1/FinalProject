package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddFeedbackCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public AddFeedbackCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommonService service = Common.getInstance();
        long alienId = Long.parseLong(request.getParameter("alienId"));
        String username = request.getParameter("username");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String feedbackText = request.getParameter("feedbackText");
        try {
            service.addFeedback(alienId, username, rating, feedbackText);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
