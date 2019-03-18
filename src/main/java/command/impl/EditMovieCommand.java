package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.UserStatus;
import service.MovieFanService;
import service.exception.ServiceException;
import service.exception.movie.InvalidMovieInformationException;
import service.impl.MovieFan;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class EditMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserAccessChecker checker;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public EditMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response, UserAccessChecker.getInstance());
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    EditMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response, UserAccessChecker checker) {
        this.service = service;
        this.request = request;
        this.response = response;
        this.checker = checker;
    }

    /**
     * Executes command of movie information change. Request provides movie ID and
     * new information: running time, budget and release date. Then service layer is
     * called to save changes. Informs person about errors (if {@link
     * InvalidMovieInformationException} was caught).
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        if (!checker.checkStatus(UserStatus.MOVIE_FAN, request) &&
                !checker.checkStatus(UserStatus.ADMIN, request)) {
            return "main";
        }
        String runningTime = request.getParameter("runningTime");
        long movieId = Long.parseLong(request.getParameter("movieId"));
        String releaseDate = request.getParameter("releaseDate");
        Date date = Date.valueOf(releaseDate);
        String budget = request.getParameter("budget");
        HttpSession session = request.getSession();
        try {
            service.editMovie(movieId, runningTime, budget, date);
        } catch (InvalidMovieInformationException e) {
            session.setAttribute("message", "message.invalid_info");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
