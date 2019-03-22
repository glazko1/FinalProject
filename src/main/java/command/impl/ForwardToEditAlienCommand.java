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

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ForwardToEditAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ForwardToEditAlienCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command forwarding to the page of alien's information change. Request
     * provides information about alien's ID, then service layer is called to get
     * information about alien (name, planet, description) and about all movies (to
     * let person to choose from a movie list), then all info's put into request.
     * @return url to forward from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String alienId = request.getParameter("alienId");
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
