package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import entity.Movie;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ForwardToEditAlienCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ForwardToEditAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    public ForwardToEditAlienCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        long alienId = Long.parseLong(request.getParameter("alienId"));
        try {
            Alien alien = service.viewAlien(alienId);
            request.setAttribute("alienId", alien.getAlienId());
            request.setAttribute("alienName", alien.getAlienName());
            request.setAttribute("planet", alien.getPlanet());
            request.setAttribute("description", alien.getDescription());
            List<Movie> movies = service.viewAllMovies();
            request.setAttribute("movies", movies);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "edit-alien";
    }
}
