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

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommonService service = Common.getInstance();
        long id = Long.parseLong(request.getParameter("alienId"));
        try {
            Pair<Alien, List<Feedback>> pair = service.viewAlien(id);
            request.setAttribute("alien", pair.getKey());
            request.setAttribute("feedbacks", pair.getValue());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "alien-page";
    }
}
