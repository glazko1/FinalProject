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

    public AddMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response);
    }

    AddMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

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
