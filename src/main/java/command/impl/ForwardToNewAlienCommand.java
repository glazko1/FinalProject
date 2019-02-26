package command.impl;

import command.Command;
import command.exception.CommandException;
import command.factory.CommandFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToNewAlienCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToNewAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommandFactory factory = CommandFactory.getInstance();
        Command command = factory.createCommand("viewAllMovies", request, response);
        command.execute();
        return "new-alien";
    }
}
