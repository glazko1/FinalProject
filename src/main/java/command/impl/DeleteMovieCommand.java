package command.impl;

import command.Command;
import command.exception.CommandException;
import service.MovieFanService;
import service.exception.ServiceException;
import service.impl.MovieFan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public DeleteMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response);
    }

    public DeleteMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long movieId = Long.parseLong(request.getParameter("movieId"));
        try {
            service.deleteMovie(movieId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
