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

    public EditMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response);
    }

    EditMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long movieId = Long.parseLong(request.getParameter("movieId"));
        String runningTime = request.getParameter("runningTime");
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
