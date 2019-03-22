package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import entity.Feedback;
import javafx.util.Pair;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAlienCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewAlienCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of getting an alien. Alien's ID is provided by request, then
     * service layer returns alien with its feedbacks (they all are put into request
     * to show on page).
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String id = request.getParameter("alienId");
        try {
            Pair<Alien, List<Feedback>> pair = service.viewAlienWithFeedbacks(id);
            request.setAttribute("alien", pair.getKey());
            request.setAttribute("feedbacks", pair.getValue());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "alien-page";
    }
}
