package command.impl;

import com.google.protobuf.ServiceException;
import command.Command;
import command.exception.CommandException;
import service.MovieFanService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class AddMovieCommand implements Command {

    private MovieFanService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

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
        String birthDate = request.getParameter("year") + "-" +
                request.getParameter("month") + "-" +
                request.getParameter("day");
        Date releaseDate = Date.valueOf(birthDate);
        try {
            service.addMovie(title, runningTime, budget, releaseDate);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
