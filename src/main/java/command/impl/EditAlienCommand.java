package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public EditAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long alienId = Long.parseLong(request.getParameter("alienId"));
        String movie = request.getParameter("movie");
        String planet = request.getParameter("planet");
        String description = request.getParameter("description");
        try {
            service.editAlien(alienId, movie, planet, description);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
