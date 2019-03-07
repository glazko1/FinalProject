package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ForwardToNewAlienCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToNewAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public ForwardToNewAlienCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        try {
            List<Movie> movies = service.viewAllMovies();
            request.setAttribute("movies", movies);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "new-alien";
    }
}
