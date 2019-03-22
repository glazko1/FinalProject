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

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewMovieCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of getting a movie. Movie ID is provided by request, then
     * service layer returns movie and it's put into request to show on page.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String movieId = request.getParameter("movieId");
        try {
            Movie movie = service.viewMovie(movieId);
            request.setAttribute("movie", movie);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "movie-page";
    }
}
