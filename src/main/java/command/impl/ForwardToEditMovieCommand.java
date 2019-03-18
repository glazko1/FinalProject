package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import entity.UserStatus;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardToEditMovieCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ForwardToEditMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ForwardToEditMovieCommand(CommonService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
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
        if (!checker.checkStatus(UserStatus.MOVIE_FAN, request) ||
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
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
