package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import service.CommonService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToEditMovieCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToEditMovieCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long movieId = Long.parseLong(request.getParameter("movieId"));
        try {
            Movie movie = service.viewMovie(movieId);
            request.setAttribute("movieId", movie.getMovieId());
            request.setAttribute("title", movie.getTitle());
            request.setAttribute("runningTime", movie.getRunningTime());
            request.setAttribute("budget", movie.getBudget());
            request.setAttribute("releaseDate", movie.getReleaseDateTimestamp());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "edit-movie";
    }
}
