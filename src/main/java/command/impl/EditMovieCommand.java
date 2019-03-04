package command.impl;

import command.Command;
import command.exception.CommandException;
import service.MovieFanService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class EditMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public EditMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long movieId = Long.parseLong(request.getParameter("movieId"));
        int runningTime = Integer.parseInt(request.getParameter("runningTime"));
        String date = request.getParameter("year") + "-" +
                request.getParameter("month") + "-" +
                request.getParameter("day");
        Date releaseDate = Date.valueOf(date);
        int budget = Integer.parseInt(request.getParameter("budget"));
        try {
            service.editMovie(movieId, runningTime, budget, releaseDate);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
