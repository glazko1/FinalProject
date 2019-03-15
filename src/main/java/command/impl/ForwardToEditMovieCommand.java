package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToEditMovieCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ForwardToEditMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ForwardToEditMovieCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command forwarding to the page of movie information change. Request
     * provides information about movie ID, then service layer returns information
     * about movie: title, running time, budget and release date. All this information's
     * put into request.
     * @return url to forward from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
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
