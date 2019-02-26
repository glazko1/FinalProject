package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewMovieCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewMovieCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long movieId = Long.parseLong(request.getParameter("movieId"));
        try {
            Movie movie = service.viewMovie(movieId);
            request.setAttribute("movie", movie);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "movie-page";
    }
}