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

public class ViewAllMoviesSortedCommand implements Command {

    private CommonService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public ViewAllMoviesSortedCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Common.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of alien specialists.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    ViewAllMoviesSortedCommand(CommonService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws CommandException {
        String sortedBy = request.getParameter("sortedBy");
        String sortType = request.getParameter("sortType");
        try {
            List<Movie> movies = service.viewAllMoviesSorted(sortedBy, sortType);
            request.setAttribute("movies", movies);
            request.setAttribute("sortedBy", sortedBy);
            request.setAttribute("sortType", sortType);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "movie-table";
    }
}
