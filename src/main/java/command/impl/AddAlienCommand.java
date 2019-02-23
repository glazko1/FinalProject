package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAlienCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public AddAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        AlienSpecialistService service = AlienSpecialist.getInstance();
        String alienName = request.getParameter("alienName");
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        String movie = request.getParameter("movie");
        try {
            service.addAlien(alienName, planet, description, movie);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
