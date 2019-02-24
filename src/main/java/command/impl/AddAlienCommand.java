package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public AddAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
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
