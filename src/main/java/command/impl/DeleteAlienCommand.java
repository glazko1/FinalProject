package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAlienCommand implements Command {

    private AlienSpecialistService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public DeleteAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(AlienSpecialist.getInstance(), request, response);
    }

    public DeleteAlienCommand(AlienSpecialistService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long alienId = Long.parseLong(request.getParameter("alienId"));
        try {
            service.deleteAlien(alienId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
