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

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ViewMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        CommonService service = Common.getInstance();
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
