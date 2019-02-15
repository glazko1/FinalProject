package command.impl;

import command.Command;
import command.exception.CommandException;
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
    public void execute() throws CommandException {
        AlienSpecialist specialist = AlienSpecialist.getInstance();
        try {
            specialist.addAlien(request, response);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
