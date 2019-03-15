package command.impl;

import command.Command;
import command.exception.CommandException;
import service.MovieFanService;
import service.exception.ServiceException;
import service.exception.movie.InvalidMovieInformationException;
import service.exception.movie.InvalidMovieTitleException;
import service.impl.MovieFan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class AddMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public AddMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    AddMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of adding a new movie. Request provides information about
     * movie: title, running time, budget and release date. Then control is transferred
     * to service layer and its method. Informs user about errors while adding by
     * showing a message (if {@link InvalidMovieTitleException} or {@link
     * InvalidMovieInformationException} were caught).
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String title = request.getParameter("title");
        String runningTime = request.getParameter("runningTime");
        String budget = request.getParameter("budget");
        String releaseDate = request.getParameter("releaseDate");
        Date date = Date.valueOf(releaseDate);
        HttpSession session = request.getSession();
        try {
            service.addMovie(title, runningTime, budget, date);
        } catch (InvalidMovieTitleException e) {
            session.setAttribute("message", "message.invalid_movie_title");
        } catch (InvalidMovieInformationException e) {
            session.setAttribute("message", "message.invalid_info");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
