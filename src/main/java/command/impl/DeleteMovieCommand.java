package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.UserStatus;
import service.MovieFanService;
import service.exception.ServiceException;
import service.impl.MovieFan;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public DeleteMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    DeleteMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of deleting a movie. Request provides alien's ID, then
     * service layer method is called.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.MOVIE_FAN, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        long movieId = Long.parseLong(request.getParameter("movieId"));
        try {
            service.deleteMovie(movieId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
