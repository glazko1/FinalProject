package command.impl;

import command.Command;
import command.exception.CommandException;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteMovieCommand implements Command {

    private AdminService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs command with default service, specified request and response.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    public DeleteMovieCommand(HttpServletRequest request, HttpServletResponse response) {
        this(Admin.getInstance(), request, response);
    }

    /**
     * Constructs command with specified service, request and response.
     * @param service service layer class with opportunities of admin.
     * @param request HTTP-request.
     * @param response HTTP-response.
     */
    DeleteMovieCommand(AdminService service, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.request = request;
        this.response = response;
    }

    /**
     * Executes command of deleting a movie. Request provides alien's ID, then
     * service layer method is called.
     * @return url to redirect from servlet.
     * @throws CommandException if {@link ServiceException} was caught.
     */
    @Override
    public String execute() throws CommandException {
        String movieId = request.getParameter("movieId");
        try {
            service.deleteMovie(movieId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "main";
    }
}
