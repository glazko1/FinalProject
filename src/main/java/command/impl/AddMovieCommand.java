package command.impl;

import command.Command;
import command.exception.CommandException;
import service.MovieFanService;
import service.exception.ServiceException;
import service.impl.MovieFan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class AddMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public AddMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(MovieFan.getInstance(), request, response);
    }

    public AddMovieCommand(MovieFanService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        String title = request.getParameter("title");
        int runningTime = Integer.parseInt(request.getParameter("runningTime"));
        int budget = Integer.parseInt(request.getParameter("budget"));
        String releaseDate = request.getParameter("releaseDate");
        Date date = Date.valueOf(releaseDate);
        try {
            service.addMovie(title, runningTime, budget, date);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
