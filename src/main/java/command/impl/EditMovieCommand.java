package command.impl;

import command.Command;
import command.exception.CommandException;
import service.MovieFanService;
import service.exception.ServiceException;
import service.exception.movie.InvalidMovieInformationException;
import service.impl.MovieFan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class EditMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public EditMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of movie fan.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    EditMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
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
        String runningTime = request.getParameter("runningTime");
        String movieId = request.getParameter("movieId");
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
