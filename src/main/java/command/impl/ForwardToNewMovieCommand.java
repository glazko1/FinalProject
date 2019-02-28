package command.impl;

import command.Command;
import command.exception.CommandException;
import service.CommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToNewMovieCommand implements Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToNewMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        return "new-movie";
    }
}
