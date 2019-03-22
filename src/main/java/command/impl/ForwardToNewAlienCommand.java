package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ForwardToNewAlienCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ForwardToNewAlienCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of user.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ForwardToNewAlienCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command forwarding to the page of adding an alien. Calls service layer
     * to get all movie names (to give person an opportunity to choose a film from the
     * list), then sets it as request attribute.
     * @return url to forward from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        try {
            List<Movie> movies = service.viewAllMovies();
            request.setAttribute("movies", movies);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "new-alien";
    }
}